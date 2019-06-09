package bigdata.reactive.actors;

import java.util.HashMap;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import bigdata.reactive.actors.calculate.CalculateActor;
import bigdata.reactive.actors.input.InputActor;
import bigdata.reactive.actors.tokenizer.ConstructTermsActor;
import bigdata.reactive.messages.BootMessage;
import bigdata.reactive.messages.DocumentListData;
import bigdata.reactive.messages.RequestCalculateMessage;
import bigdata.reactive.messages.ResultRequest;
import bigdata.reactive.messages.TermFrequencyListData;
import bigdata.reactive.messages.TermListData;

public class MasterActor extends AbstractActor{

	private DocumentListData documents;
	private TermFrequencyListData term_frequency;
	
	// actors list
	ActorRef input_actor = getContext().actorOf(InputActor.props(), "input");
	ActorRef construct_terms_actor = getContext().actorOf(ConstructTermsActor.props(), "constructor");
	ActorRef calculator_actor = getContext().actorOf(CalculateActor.props(), "calculator");
	
	
	@Override
	public Receive createReceive() {

		return receiveBuilder()
				.match(BootMessage.class, msg -> {
					input_actor.tell(msg, getSelf());
					this.wait(1000);
					input_actor.tell(new ResultRequest(), getSelf());
				})
				.match(DocumentListData.class, msg ->{
					System.out.println(msg.get_documents().size() + " documents successfully read");
					this.documents = msg;
					construct_terms_actor.tell(msg, getSelf());
				})
				.match(TermListData.class, msg ->{
					System.out.println(msg.get_terms_table().size() + " terms found");
					calculator_actor.tell(new RequestCalculateMessage(documents, msg), getSelf());
					this.wait(1000);
					calculator_actor.tell(new ResultRequest(), getSelf());					
				})
				.match(TermFrequencyListData.class, msg ->{
					System.out.println(msg.getTable().size() + " cells in the term frequency table");
					this.term_frequency = msg;
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
		return Props.create(MasterActor.class);
	}

}
