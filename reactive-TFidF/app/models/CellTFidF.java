package models;

public class CellTFidF {

	private String id;
	private String url_document;
	private String term;
	private double value;
	
	public CellTFidF(String id, String url_document, String term, double value) {
		super();
		this.id = id;
		this.url_document = url_document;
		this.term = term;
		this.value = value;
	}

	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getUrl_document() {
		return url_document;
	}

	public void setUrl_document(String url_document) {
		this.url_document = url_document;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}	
	
}
