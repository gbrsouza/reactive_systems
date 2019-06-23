package bigdata.reactive.messages.requests;

import bigdata.reactive.messages.data.DocumentListData;
import bigdata.reactive.messages.data.TermListData;

public class RequestCalculateMessage {

	private final DocumentListData document_list;
	private final TermListData term_list;
	
	public RequestCalculateMessage(DocumentListData document_list, TermListData term_list) {
		super();
		this.document_list = document_list;
		this.term_list = term_list;
	}
	
	public DocumentListData get_document_list () {
		return this.document_list;
	}
	
	public TermListData term_list () {
		return this.term_list;
	}
	
	
	
}
