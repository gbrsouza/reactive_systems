package bigdata.reactive.messages;

import java.util.Collections;
import java.util.List;

public class UrlsDocumentsData {

	private final List<String> urls;

	public UrlsDocumentsData(List<String> urls) {
		super();
		this.urls = Collections.unmodifiableList(urls);
	}

	public List<String> getUrls() {
		return urls;
	}
	
}
