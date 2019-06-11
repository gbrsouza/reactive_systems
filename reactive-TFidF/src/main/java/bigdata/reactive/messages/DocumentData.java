package bigdata.reactive.messages;

import java.util.Collections;
import java.util.Map;

public class DocumentData {

	private final String url;
	private final int number_terms;
	private final Map<String, Integer> terms;
	
	public DocumentData(String url, int number_terms, Map<String, Integer> terms) {
		super();
		this.url = url;
		this.number_terms = number_terms;
		this.terms = Collections.unmodifiableMap(terms);
	}

	public String get_url() {
		return url;
	}

	public int get_number_terms() {
		return number_terms;
	}

	public Map<String, Integer> get_terms() {
		return terms;
	}
	
	
	
	
}
