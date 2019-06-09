package bigdata.reactive.messages;

import java.util.List;

import bigdata.reactive.CellMultiTable;

public class CellListMessage {

	private final List<CellMultiTable> cells;

	public CellListMessage(List<CellMultiTable> cells) {
		super();
		this.cells = cells;
	}

	public List<CellMultiTable> getCells() {
		return cells;
	}

	
}
