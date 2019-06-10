package bigdata.reactive.actors.calculate;

import java.util.HashMap;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.InverseDocListMessage;
import bigdata.reactive.messages.InverseDocumentData;
import bigdata.reactive.messages.ResultRequest;

public class AggregateInverseDocument extends AbstractActor {
	
	private HashMap<String, Double> inv_doc = new HashMap<>();

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(InverseDocumentData.class, msg ->{
					inv_doc.put(msg.getTerm(), msg.getValue());
				})
				.match(ResultRequest.class, msg ->{
					getSender().tell(new InverseDocListMessage(inv_doc), getSelf());
				})
				.build();
	}
	
	public static Props props () {
		return Props.create(AggregateInverseDocument.class);
	}
	
}
