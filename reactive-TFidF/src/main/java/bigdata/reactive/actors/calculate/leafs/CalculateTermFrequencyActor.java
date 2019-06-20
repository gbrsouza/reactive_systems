package bigdata.reactive.actors.calculate.leafs;

import static akka.actor.SupervisorStrategy.restart;

import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import bigdata.reactive.actors.calculate.AggregateCellList;
import bigdata.reactive.actors.calculate.supervisors.TermFrequencyActor;
import bigdata.reactive.messages.*;
import bigdata.reactive.messages.data.DocumentData;
import bigdata.reactive.messages.data.TableData;
import bigdata.reactive.messages.requests.RequestCalculateMessage;
import bigdata.reactive.messages.requests.RequestTermFrequencyMessage;
import bigdata.reactive.messages.requests.ResultRequest;
import scala.concurrent.duration.Duration;

public class CalculateTermFrequencyActor extends AbstractActor {

	private int total_messages;
	private int actual_messages = 0;

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
	ActorRef supervision_actor;

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestCalculateMessage.class, msg->{
					supervision_actor = getSender();
					List<DocumentData> docs = msg.get_document_list().get_documents();

					this.actual_messages = 0;
					this.total_messages = docs.size();

					docs.stream().forEach(d ->
					{
						routerTF.route(
							new RequestTermFrequencyMessage(d, msg.term_list()),
							getSelf());
					});
				})
				.match(CellListMessage.class, msg ->{
					this.actual_messages++;
					aggregate_tf.tell(msg, getSelf());
					if ( this.actual_messages == this.total_messages )
						aggregate_tf.tell(new ResultRequest(), getSelf());
				})
				.match(TableData.class, msg-> {
					supervision_actor.tell(msg, getSelf());
				}).build();
	}
	
	public static Props props () {
		return Props.create(CalculateTermFrequencyActor.class);
	}

}
