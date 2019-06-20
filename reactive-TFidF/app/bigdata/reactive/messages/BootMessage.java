package bigdata.reactive.messages;

public class BootMessage {

	private final String url_documents;   //<! link for all URL's documents
	private final String url_stop_wordls; //<! link for stop words file
	
	/**
	 * construct a object BootMessage
	 * @param url_documents   the link for all URL's documents
	 * @param url_stop_wordls the link for stop words file
	 */
	public BootMessage(String url_documents, String url_stop_wordls) {
		super();
		this.url_documents = url_documents;
		this.url_stop_wordls = url_stop_wordls;
	}

	
	// Getters and Setters
	
	public String getUrl_documents() {
		return url_documents;
	}

	public String getUrl_stop_wordls() {
		return url_stop_wordls;
	}
	
	
	
}
