package bigdata.reactive.actors.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.data.UrlsDocumentsData;

public class ReadLinksActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class , msg -> {
					getSender().tell(readUrls(msg), getSelf());
				}).build();
	}
	
	/**
	 * read the file and separate all urls for read.
	 * @param m  the url to file
	 * @return all urls in the file
	 */
	private UrlsDocumentsData readUrls ( String m ){
		
		// create the list of urls
		ArrayList<String> urls = new ArrayList<String>();
		
		try {
			
			// open file
			FileReader file = new FileReader( m );
			BufferedReader readFile = new BufferedReader(file);
			String line = readFile.readLine();
			int count = 0;
			// read all lines (urls)
			while (line != null) {
				line.trim();
				urls.add(line);
				line = readFile.readLine();
				count ++;
			}
			
			System.out.println( count + " documents to read found ");
			// close file
			file.close();
		} catch (IOException e1) {
			System.err.println("could not open file " + m 
					+ " " + e1.getMessage());
		}
		
		return new UrlsDocumentsData(urls);
	}
	
	/**
	 * method to return a Props
	 * @return return a this class Props 
	 */
	public static Props props () {
		return Props.create(ReadLinksActor.class);
	}

}
