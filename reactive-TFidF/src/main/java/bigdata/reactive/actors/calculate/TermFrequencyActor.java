package bigdata.reactive.actors.calculate;

import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.CellMultiTable;
import bigdata.reactive.messages.CellListMessage;
import bigdata.reactive.messages.RequestTermFrequencyMessage;

public class TermFrequencyActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(RequestTermFrequencyMessage.class, msg->{
					getSender().tell(term_frequency(msg), getSelf());
				}).build();
	}
	
	private CellListMessage term_frequency ( RequestTermFrequencyMessage message ) {
		
		double occurrence_number = 0.0;
		double terms_number = 1.0;
		double value = 0.0;
		
		List<CellMultiTable> cells = new ArrayList<CellMultiTable>();
		
		for (String s : message.getTerms().keySet() ) {
			occurrence_number = (double) message.get_occurrences(s);
			if (occurrence_number != 0.0) {
				terms_number = (double) message.getDocument().get_number_terms();
				value = occurrence_number/ terms_number;
				
				cells.add(new CellMultiTable(message.document_name(), s, value));
			}
		}
		
		return new CellListMessage(cells);
	}
	
	public static Props props () {
		return Props.create(TermFrequencyActor.class);
	}

}
