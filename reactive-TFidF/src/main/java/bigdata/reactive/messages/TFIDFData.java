package bigdata.reactive.messages;

import bigdata.reactive.CellMultiTable;

import java.util.Map;

public class TFIDFData {

    private final CellMultiTable tfidf;

    public TFIDFData(CellMultiTable tfidf) {
        this.tfidf = tfidf;
    }

    public CellMultiTable getTfidf() {
        return tfidf;
    }
}
