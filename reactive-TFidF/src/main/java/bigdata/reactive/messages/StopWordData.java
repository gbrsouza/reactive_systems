package bigdata.reactive.messages;

import java.util.HashMap;

public class StopWordData {

	/**
	 * Key   - the stop word
	 * Value - the id
	 */
	private final HashMap<String, Integer> stopWords;
	
	/**
	 * Construct a object to StopWordData
	 * @param stopWords
	 */
	public StopWordData ( 
		 HashMap<String, Integer> stopWords )
	{ this.stopWords = stopWords; }
	
	/**
	 * Get the stop words in the message
	 * @return the stop words
	 */
	public HashMap<String, Integer> 
	getStopWords ()
	{ return this.stopWords; }
	
	public String test() { return "deu bom\n"; }
	
}
