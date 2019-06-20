package bigdata.reactive.actors.calculate.supervisors;


import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.data.DocumentData;
import bigdata.reactive.messages.data.InverseDocumentData;
import bigdata.reactive.messages.requests.RequestInvDocMessage;

public class InverseDocumentActor extends AbstractActor{

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestInvDocMessage.class, msg -> {
					getSender().tell(calc(msg), getSelf());
				})
				.build();
	}
	
	private InverseDocumentData calc ( RequestInvDocMessage msg ) {	
		
		List<DocumentData> docs = msg.getDocuments().get_documents();
		double number_docs = (double) docs.size();
		int count = 0;
		
		for ( DocumentData d : docs )
			if (d.get_terms().containsKey(msg.getTerm()))
				count++;
		
		double value = Math.log(number_docs/(double) count);
		
		return new InverseDocumentData(msg.getTerm(), value);
	}
	
	public static Props props () {
		return Props.create(InverseDocumentActor.class);
	}

}
