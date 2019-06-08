package bigdata.reactive.actors.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.DocumentData;
import bigdata.reactive.messages.ReadDocumentMessage;

public class ReadDocumentActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(ReadDocumentMessage.class, msg -> {
					getSender().tell(this.read_document(msg), self());
				}).build();
	}
	
	private DocumentData read_document ( ReadDocumentMessage message ) {
		
		int processed_terms = 0;
		HashMap<String, Integer> terms = new HashMap<>();
		
		try {
			
			FileReader file = new FileReader(message.get_url().trim());
			BufferedReader readFile = new BufferedReader(file);
			
			String line = readFile.readLine(); // read the first line
			List<String> tokenized_terms = new ArrayList<String>();
			
			while (line != null) {
				
		        tokenized_terms = tokenizer_terms( line );
				
		        for (String s : tokenized_terms) {
		        
		        	if ( !s.trim().equals("") && !message.is_stop_word(s)) {
						processed_terms++;
						if ( terms.containsKey(s) )
							terms.replace(s, terms.get(s)+1);
						else terms.put(s, 1);
					}
		       
		        }
				
		        line = readFile.readLine(); // next line
			}
			
			file.close();
		} catch (IOException e1) {
			System.err.println("could not open file " + message.get_url() 
					+ " " + e1.getMessage());
		}
		
		System.out.println(processed_terms + " termos processados em " + message.get_url());
		return new DocumentData(message.get_url(), processed_terms, terms);
	}
	
	private List<String> tokenizer_terms( String line ){
		
		List<String> terms = new ArrayList<String>();
		String[] parsed_terms;
		
		// eliminates line punctuation
    	parsed_terms = line.trim().split("\\p{Punct}");
    	
    	for ( String t : parsed_terms )
        {
        	// separate words (terms)
        	String[] single_terms = t.trim().split("\\s+");
        	for ( String s : single_terms )
        		terms.add(s);
        }
    	
    	return terms;
	}
	
	/**
	 * method to return a Props
	 * @return return a this class Props 
	 */
	public static Props props () {
		return Props.create(ReadDocumentActor.class);
	}

}
