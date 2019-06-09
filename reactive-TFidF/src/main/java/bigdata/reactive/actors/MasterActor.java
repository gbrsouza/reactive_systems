package bigdata.reactive.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import bigdata.reactive.actors.input.InputActor;
import bigdata.reactive.actors.input.ReadLinksActor;
import bigdata.reactive.actors.input.StopWordActor;
import bigdata.reactive.messages.BootMessage;
import bigdata.reactive.messages.DocumentListData;
import bigdata.reactive.messages.ResultRequest;
import bigdata.reactive.messages.StopWordData;
import bigdata.reactive.messages.UrlsDocumentsData;

public class MasterActor extends AbstractActor{

	ActorRef input_actor = getContext().actorOf(InputActor.props(), "input");
	
	@Override
	public Receive createReceive() {

		return receiveBuilder()
				.match(BootMessage.class, msg -> {
					input_actor.tell(msg, getSelf());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					input_actor.tell(new ResultRequest(), getSelf());
				})
				.match(DocumentListData.class, msg ->{
					System.out.println(msg.getDocuments().size() + " documents reads");
				}).build();
	}
	
	public static Props props () {
		return Props.create(MasterActor.class);
	}

}
