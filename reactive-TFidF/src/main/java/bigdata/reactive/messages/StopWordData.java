package bigdata.reactive.messages;

import java.util.Collections;
import java.util.Map;

public class StopWordData {

	/**
	 * Key   - the stop word
	 * Value - the id
	 */
	private final Map<String, Integer> stop_words;
	
	/**
	 * Construct a object to StopWordData
	 * @param stopWords
	 */
	public StopWordData ( 
		 Map<String, Integer> stop_words )
	{ this.stop_words = Collections.unmodifiableMap(stop_words); }
	
	/**
	 * Get the stop words in the message
	 * @return the stop words
	 */
	public Map<String, Integer> 
	getStopWords ()
	{ return this.stop_words; }
	
	public boolean is_stop_word ( String key ) {
		if (stop_words.containsKey(key))
			return true;
		else return false;
	}
	
	public String test() { return "deu bom\n"; }
	
}
