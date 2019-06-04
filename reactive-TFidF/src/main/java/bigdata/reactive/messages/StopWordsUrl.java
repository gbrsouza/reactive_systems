package bigdata.reactive.messages;

public class StopWordsUrl {

	// The link to read the file with stop words
	private final String url;
	
	/**
	 * Construct a object to StopWordsUrl
	 * @param url the URL to file with stop words
	 */
	public StopWordsUrl ( String url )
	{ this.url = url; }
	
	/**
	 * Get the URL to file with stop words
	 * @return the URL 
	 */
	public String getUrl ()
	{ return this.url; }
}
