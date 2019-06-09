package bigdata.reactive.actors.calculate;

import static akka.actor.SupervisorStrategy.restart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.japi.pf.DeciderBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import bigdata.reactive.CellMultiTable;
import bigdata.reactive.messages.CellListMessage;
import bigdata.reactive.messages.DocumentData;
import bigdata.reactive.messages.RequestCalculateMessage;
import bigdata.reactive.messages.RequestTermFrequencyMessage;
import bigdata.reactive.messages.ResultRequest;
import bigdata.reactive.messages.TermFrequencyListData;
import scala.concurrent.duration.Duration;

public class CalculateActor extends AbstractActor {
	
	// create supervisor strategy
	final SupervisorStrategy routingSupervisorStrategy =
		    new OneForOneStrategy(
		      10,
		      Duration.create("10 seconds"),
		      DeciderBuilder
		          .match(RuntimeException.class, ex -> restart())
		          .build()
		    );
	
	// create the router to calculate term frequency 
	Router routerTF;

	{
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < 4; i++) {
	    	ActorRef r = getContext().actorOf(Props.create(TermFrequencyActor.class));
	    	getContext().watch(r);
	    	routees.add(new ActorRefRoutee(r));
	    }
	    routerTF = new Router(new RoundRobinRoutingLogic(), routees);
	}
	
	// list actors
	ActorRef aggregate_tf = getContext().actorOf(AggregateCellList.props(), "agg_tf"); 
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestCalculateMessage.class, msg->{
					List<DocumentData> docs = msg.get_document_list().get_documents();
					docs.stream().forEach(d ->
					{
						routerTF.route(
							new RequestTermFrequencyMessage(d, msg.term_list()),
							getSelf());
					});
				})
				.match(CellListMessage.class, msg ->{
					aggregate_tf.tell(msg, getSelf());
				})
				.match(ResultRequest.class, msg-> {
					aggregate_tf.forward(msg, getContext());
				}).build();
	}
	
	public static Props props () {
		return Props.create(CalculateActor.class);
	}

}
