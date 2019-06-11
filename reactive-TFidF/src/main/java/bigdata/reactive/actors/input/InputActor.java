package bigdata.reactive.actors.input;

import java.util.ArrayList;
import java.util.List;

import akka.actor.*;
import static akka.actor.SupervisorStrategy.*;
import scala.concurrent.duration.Duration;
import akka.japi.pf.DeciderBuilder;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import bigdata.reactive.messages.BootMessage;
import bigdata.reactive.messages.DocumentData;
import bigdata.reactive.messages.ReadDocumentMessage;
import bigdata.reactive.messages.ResultRequest;
import bigdata.reactive.messages.StopWordData;
import bigdata.reactive.messages.UrlsDocumentsData;

public class InputActor extends AbstractActor{

	private StopWordData stop_words;
	private BootMessage boot_message;
	
	// create actors to use
	ActorRef stop_word_actor = getContext().actorOf(StopWordActor.props(), "stop_word");
	ActorRef read_links_actor = getContext().actorOf(ReadLinksActor.props(), "read_links");
	
	// create supervisor strategy
	final SupervisorStrategy routingSupervisorStrategy =
		    new OneForOneStrategy(
		      10,
		      Duration.create("10 seconds"),
		      DeciderBuilder
		          .match(RuntimeException.class, ex -> restart())
		          .build()
		    );
	
	// create the router to read documents 
	Router router;

	{
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < 10; i++) {
	    	ActorRef r = getContext().actorOf(Props.create(ReadDocumentActor.class));
	    	getContext().watch(r);
	    	routees.add(new ActorRefRoutee(r));
	    }
	    router = new Router(new RoundRobinRoutingLogic(), routees);
	       
	}
	
	// aggregate the list of documents
	ActorRef aggregate_actor = getContext().actorOf(AggregateDocumentActor.props(), "aggregate");
	
	// principal method
	@Override
	public Receive createReceive() {
		
		return receiveBuilder()
				.match(BootMessage.class, msg-> {
					this.boot_message = msg;
					stop_word_actor.tell(msg.getUrl_stop_wordls(), getSelf());
				})
				.match(StopWordData.class, msg -> {
					this.stop_words = msg;
					read_links_actor.tell(boot_message.getUrl_documents(), getSelf());
				})
				.match(UrlsDocumentsData.class, msg->{
					
					for ( String s : msg.getUrls() ) 
						router.route(new ReadDocumentMessage(s, this.stop_words), getSelf());
				})
			    .match(DocumentData.class, msg ->{
					aggregate_actor.tell(msg, getSelf());			
				})
			    .match(ResultRequest.class, msg ->{
			    	aggregate_actor.forward(msg, getContext());
			    }).build(); 
	}
	
	/**
	 * method to return a Props
	 * @return return a this class Props 
	 */
	public static Props props () {
		return Props.create(InputActor.class);
	}

}
