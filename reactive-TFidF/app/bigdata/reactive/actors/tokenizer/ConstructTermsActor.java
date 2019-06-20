package bigdata.reactive.actors.tokenizer;

import java.util.HashMap;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.data.DocumentData;
import bigdata.reactive.messages.data.DocumentListData;
import bigdata.reactive.messages.data.TermListData;

public class ConstructTermsActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(DocumentListData.class, msg->{
					getSender().tell(construct_terms(msg), getSelf());
				}).build();
	}

	private TermListData construct_terms (DocumentListData document) {
		
		HashMap<String, Integer> terms_table = new HashMap<>();
		int value = 0;
		
		for ( DocumentData d : document.get_documents() ) {
			
			for ( String s : d.get_terms().keySet() ) {
				if ( terms_table.containsKey(s) ) {
					value = terms_table.get(s);
					value += d.get_terms().get(s);
					terms_table.put(s, value);
				}else {
					value = d.get_terms().get(s);
					terms_table.put(s, value);
				}
			}
		}
		
		return new TermListData(terms_table);
	}
	
	public static Props props () {
		return Props.create(ConstructTermsActor.class);
	}
	
}
