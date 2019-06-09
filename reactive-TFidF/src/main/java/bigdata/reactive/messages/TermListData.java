package bigdata.reactive.messages;

import java.util.HashMap;

public class TermListData {

	private final HashMap<String, Integer> terms_table;

	public TermListData(HashMap<String, Integer> terms_table) {
		super();
		this.terms_table = terms_table;
	}
	
	public HashMap<String, Integer> get_terms_table (){ 
		return this.terms_table;
	}
	
	
}
