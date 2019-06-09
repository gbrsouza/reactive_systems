package bigdata.reactive.messages;

import java.util.List;

public class DocumentListData {

	private final List<DocumentData> documents;

	public DocumentListData(List<DocumentData> documents) {
		super();
		this.documents = documents;
	}

	public List<DocumentData> getDocuments() {
		return documents;
	}
	
}
