package bigdata.reactive.messages;

import java.util.HashMap;

import bigdata.reactive.CellMultiTable;

public class TermFrequencyListData {

	private final HashMap<CellMultiTable, Integer> table;

	public TermFrequencyListData(HashMap<CellMultiTable, Integer> table) {
		super();
		this.table = table;
	}

	public HashMap<CellMultiTable, Integer> getTable() {
		return table;
	}
	
	
}
