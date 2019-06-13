package bigdata.reactive.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import bigdata.reactive.actors.calculate.CalculateActor;
import bigdata.reactive.actors.input.InputActor;
import bigdata.reactive.actors.tokenizer.ConstructTermsActor;
import bigdata.reactive.messages.*;

public class MasterActor extends AbstractActor{

	private DocumentListData documents;
	
	// actors list
	ActorRef input_actor = getContext().actorOf(InputActor.props(), "input");
	ActorRef construct_terms_actor = getContext().actorOf(ConstructTermsActor.props(), "constructor");
	ActorRef calculator_actor = getContext().actorOf(CalculateActor.props(), "calculator");

	ActorRef supervision_actor;
	
	@Override
	public Receive createReceive() {

		return receiveBuilder()
				.match(BootMessage.class, msg -> {
					this.supervision_actor = getSender();
					input_actor.tell(msg, getSelf());
				})
				.match(DocumentListData.class, msg ->{
					System.out.println(msg.get_documents().size() + " documents successfully read");
					this.documents = msg;
					construct_terms_actor.tell(msg, getSelf());
				})
				.match(TermListData.class, msg ->{
					System.out.println(msg.get_terms_table().size() + " terms found");
					calculator_actor.tell(new RequestCalculateMessage(documents, msg), getSelf());				
				})
				.match(TableData.class, msg ->{
					supervision_actor.tell(new ResultRequest(), getSelf());
				})
				.build();
	}
	
	public static Props props () {
		return Props.create(MasterActor.class);
	}

}
