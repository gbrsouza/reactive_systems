package bigdata.reactive.messages;

import bigdata.reactive.messages.data.StopWordData;

public class ReadDocumentMessage {

	private final String url_document;
	private final StopWordData stop_words;
	
	public ReadDocumentMessage(String url_document, StopWordData stop_words) {
		super();
		this.url_document = url_document;
		this.stop_words = stop_words;
	}
	
	public String get_url () {
		return this.url_document;
	}
	
	public boolean is_stop_word( String key ) {
		return stop_words.is_stop_word(key);
	}
	
	
}
