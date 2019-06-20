package bigdata.reactive.messages.data;

import java.util.Collections;
import java.util.Map;

public class TermListData {

	private final Map<String, Integer> terms_table;

	public TermListData(Map<String, Integer> terms_table) {
		super();
		this.terms_table = Collections.unmodifiableMap(terms_table);
	}
	
	public Map<String, Integer> get_terms_table (){ 
		return this.terms_table;
	}
	
	
}
