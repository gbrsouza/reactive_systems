package bigdata.reactive.actors.calculate;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import bigdata.reactive.messages.InverseDocListMessage;
import bigdata.reactive.messages.RequestCalculateMessage;
import bigdata.reactive.messages.ResultRequest;
import bigdata.reactive.messages.TermFrequencyListData;

public class CalculateActor extends AbstractActor{

	private TermFrequencyListData term_frequency;
	private InverseDocListMessage inverse_document;
	private RequestCalculateMessage data;
	
	// Actor List
	ActorRef term_frequency_actor = getContext().actorOf(CalculateTermFrequencyActor.props());
	ActorRef inverse_distance_actor = getContext().actorOf(CalculateInverseDocumentActor.props());

	ActorRef supervision_actor;

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestCalculateMessage.class, msg ->{
					this.supervision_actor = getSender();
					this.data = msg;
					term_frequency_actor.tell(msg, getSelf());
				})
				.match(TermFrequencyListData.class, msg ->{
					System.out.println(msg.getTable().size() + " cells in the term frequency table");
					this.term_frequency = msg;
					inverse_distance_actor.tell(data, getSelf());
				})
				.match(InverseDocListMessage.class, msg ->{
					System.out.println(msg.getInv_doc().size() + " cells in the inverse document table");
					this.inverse_document = msg;
					this.supervision_actor.tell(new ResultRequest(), getSelf());
				})
				.build();
	}
	
	private void wait (int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Props props () {
		return Props.create(CalculateActor.class);
	}

}
