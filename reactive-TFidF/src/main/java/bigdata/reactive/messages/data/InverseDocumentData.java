package bigdata.reactive.messages.data;

public class InverseDocumentData {

	private final String term;
	private final double value;
	
	public InverseDocumentData(String term, double value) {
		super();
		this.term = term;
		this.value = value;
	}

	public String getTerm() {
		return term;
	}

	public double getValue() {
		return value;
	}
	
	
	
}
