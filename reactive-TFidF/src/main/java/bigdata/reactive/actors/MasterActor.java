package bigdata.reactive.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import bigdata.reactive.actors.input.StopWordActor;
import bigdata.reactive.messages.StopWordsUrl;

public class MasterActor extends AbstractActor{

	ActorRef stopWordActor = getContext().actorOf(StopWordActor.props(), "stopWord");
	
	@Override
	public Receive createReceive() {

		return receiveBuilder()
				.match(StopWordsUrl.class, msg -> {stopWordActor.forward(msg, getContext());})
				.build();
	}
	
	public static Props props () {
		return Props.create(MasterActor.class);
	}

}
