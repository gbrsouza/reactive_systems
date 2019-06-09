package bigdata.reactive.messages;

import java.util.HashMap;

public class DocumentData {

	private final String url;
	private final int number_terms;
	private final HashMap<String, Integer> terms;
	
	public DocumentData(String url, int number_terms, HashMap<String, Integer> terms) {
		super();
		this.url = url;
		this.number_terms = number_terms;
		this.terms = terms;
	}

	public String get_url() {
		return url;
	}

	public int get_number_terms() {
		return number_terms;
	}

	public HashMap<String, Integer> get_terms() {
		return terms;
	}
	
	
	
	
}
