package bigdata.reactive.messages;

import java.util.HashMap;

public class RequestTermFrequencyMessage {

	private final DocumentData document;
	private final TermListData terms;
	
	public RequestTermFrequencyMessage(DocumentData document, TermListData terms) {
		super();
		this.document = document;
		this.terms = terms;
	}

	public DocumentData getDocument() {
		return document;
	}

	public HashMap<String, Integer> getTerms() {
		return terms.get_terms_table();
	}
	
	public int get_occurrences( String s ) {
		if ( document.get_terms().containsKey(s) )
			return document.get_terms().get(s);
		else return 0;
	}
	
	public String document_name () {
		return document.get_url();
	}
	
}
