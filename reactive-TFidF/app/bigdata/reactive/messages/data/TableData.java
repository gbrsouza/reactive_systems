package bigdata.reactive.messages.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import bigdata.reactive.CellMultiTable;

public class TableData {

	private final Map<CellMultiTable, Double> table;

	public TableData(HashMap<CellMultiTable, Double> table) {
		super();
		this.table = Collections.unmodifiableMap(table);
	}

	public Map<CellMultiTable, Double> getTable() {
		return table;
	}
	
	
}
