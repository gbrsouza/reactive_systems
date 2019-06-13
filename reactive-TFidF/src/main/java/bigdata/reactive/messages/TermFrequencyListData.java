package bigdata.reactive.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import bigdata.reactive.CellMultiTable;

public class TermFrequencyListData {

	private final Map<CellMultiTable, Integer> table;

	public TermFrequencyListData(HashMap<CellMultiTable, Integer> table) {
		super();
		this.table = Collections.unmodifiableMap(table);
	}

	public Map<CellMultiTable, Integer> getTable() {
		return table;
	}
	
	
}
