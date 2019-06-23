package bigdata.reactive.actors.input;

import akka.actor.Props;
import bigdata.reactive.messages.data.DocumentData;
import bigdata.reactive.messages.data.DocumentListData;
import bigdata.reactive.messages.requests.ResultRequest;

import java.util.ArrayList;

import akka.actor.AbstractActor;

public class AggregateDocumentActor extends AbstractActor {

	private ArrayList<DocumentData> result = new ArrayList<>();
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(DocumentData.class, msg ->{
					result.add(msg);
				})
				.match(ResultRequest.class, msg -> {
					getSender().tell(new DocumentListData(result), self());
				}).build();
	}

	public static Props props() {
		return Props.create(AggregateDocumentActor.class);
	}
	
}
