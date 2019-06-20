package bigdata.reactive.messages.requests;

import bigdata.reactive.messages.data.DocumentListData;

public class RequestInvDocMessage {

	private final String term;
	private final DocumentListData documents;
	
	public RequestInvDocMessage(String term, DocumentListData documents) {
		super();
		this.term = term;
		this.documents = documents;
	}

	public String getTerm() {
		return term;
	}

	public DocumentListData getDocuments() {
		return documents;
	}
	
	
}
