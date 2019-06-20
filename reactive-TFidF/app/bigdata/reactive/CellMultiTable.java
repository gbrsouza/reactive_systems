package bigdata.reactive;

public class CellMultiTable {

	private final String document_url;
	private final String term;
	private Double value;
	
	public CellMultiTable(String document_url, String term, Double value) {
		super();
		this.document_url = document_url;
		this.term = term;
		this.value = value;
	}
		
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDocument_url() {
		return document_url;
	}

	public String getTerm() {
		return term;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		return o.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public String toString() {
		return "{" +
			   document_url +
			   "," +
			   term;
	}
	
	
	
}
