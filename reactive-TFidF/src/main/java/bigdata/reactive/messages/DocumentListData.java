package bigdata.reactive.messages;

import java.util.Collections;
import java.util.List;

public class DocumentListData {

	private final List<DocumentData> documents;

	public DocumentListData(List<DocumentData> documents) {
		super();
		this.documents = Collections.unmodifiableList(documents);
	}

	public List<DocumentData> get_documents() {
		return documents;
	}
	
}
