package bigdata.reactive.actors.calculate;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import bigdata.reactive.actors.calculate.leafs.CalculateInverseDocumentActor;
import bigdata.reactive.actors.calculate.leafs.CalculateTFIDFActor;
import bigdata.reactive.actors.calculate.leafs.CalculateTermFrequencyActor;
import bigdata.reactive.messages.*;
import bigdata.reactive.messages.data.TFIDFDataMessage;
import bigdata.reactive.messages.data.TableData;
import bigdata.reactive.messages.requests.RequestCalculateMessage;
import bigdata.reactive.messages.requests.RequestCalculatorTFIDFMessage;

public class CalculateActor extends AbstractActor{

	private TableData term_frequency;
	private InverseDocListMessage inverse_document;
	private RequestCalculateMessage data;
	
	// Actor List
	ActorRef term_frequency_actor = getContext().actorOf(CalculateTermFrequencyActor.props());
	ActorRef inverse_distance_actor = getContext().actorOf(CalculateInverseDocumentActor.props());
	ActorRef tfidf_actor = getContext().actorOf(CalculateTFIDFActor.props());

	
	ActorRef supervision_actor;

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestCalculateMessage.class, msg ->{
					this.supervision_actor = getSender();
					this.data = msg;
					term_frequency_actor.tell(msg, getSelf());
				})
				.match(TableData.class, msg ->{
					System.out.println(msg.getTable().size() + " cells in the term frequency table");
					this.term_frequency = msg;
					inverse_distance_actor.tell(data, getSelf());
				})
				.match(InverseDocListMessage.class, msg ->{
					System.out.println(msg.getInv_doc().size() + " cells in the inverse document table");
					this.inverse_document = msg;
					tfidf_actor.tell(new RequestCalculatorTFIDFMessage(
						inverse_document.getInv_doc(),
						term_frequency.getTable(),
						data.get_document_list().get_documents(),
						data.term_list().get_terms_table()
					), getSelf());
				})
				.match(TFIDFDataMessage.class, msg -> {
					this.supervision_actor.tell(msg.getTable(), getSelf());
				})
				.build();
	}

	public static Props props () {
		return Props.create(CalculateActor.class);
	}

}
