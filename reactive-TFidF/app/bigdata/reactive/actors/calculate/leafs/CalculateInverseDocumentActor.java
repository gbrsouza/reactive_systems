package bigdata.reactive.actors.calculate.leafs;

import static akka.actor.SupervisorStrategy.restart;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import bigdata.reactive.actors.calculate.AggregateInverseDocument;
import bigdata.reactive.actors.calculate.supervisors.InverseDocumentActor;
import bigdata.reactive.messages.*;
import bigdata.reactive.messages.data.InverseDocumentData;
import bigdata.reactive.messages.requests.RequestCalculateMessage;
import bigdata.reactive.messages.requests.RequestInvDocMessage;
import bigdata.reactive.messages.requests.ResultRequest;
import scala.concurrent.duration.Duration;

public class CalculateInverseDocumentActor extends AbstractActor {

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
	
	// create the router to calculate inverse document
	Router router;

	{
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < 4; i++) {
	    	ActorRef r = getContext().actorOf(Props.create(InverseDocumentActor.class));
	    	getContext().watch(r);
	    	routees.add(new ActorRefRoutee(r));
	    }
	    router = new Router(new RoundRobinRoutingLogic(), routees);
	}
	
	// actor List
	ActorRef agg_inv_doc = getContext().actorOf(AggregateInverseDocument.props());
	ActorRef supervision_actor;
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestCalculateMessage.class, msg->{
					supervision_actor = getSender();

					Set<String> terms = msg.term_list().get_terms_table().keySet();
					this.actual_messages = 0;
					this.total_messages = terms.size();

					for ( String t : terms ) {
						router.route(
							new RequestInvDocMessage(t, msg.get_document_list()),
							getSelf());
					}
				})
				.match(InverseDocumentData.class, msg->{
					actual_messages++;
					agg_inv_doc.tell(msg, getSelf());
					if ( actual_messages == total_messages )
						agg_inv_doc.tell(new ResultRequest(), getSelf());
				})
				.match(InverseDocListMessage.class, msg ->{
					supervision_actor.tell(msg, getSelf());
				})
				.build();
	}

	public static Props props () {
		return Props.create(CalculateInverseDocumentActor.class);
	}
	
}
