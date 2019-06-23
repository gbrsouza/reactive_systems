package bigdata.reactive.messages.requests;

public class RequestQueryByTerm {

	private final String term;

	public RequestQueryByTerm(String term) {
		super();
		this.term = term;
	}

	public String getTerm() {
		return term;
	}

}
