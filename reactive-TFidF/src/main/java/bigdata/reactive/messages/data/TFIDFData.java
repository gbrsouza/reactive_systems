package bigdata.reactive.messages.data;

import bigdata.reactive.CellMultiTable;


public class TFIDFData {

    private final CellMultiTable tfidf;

    public TFIDFData(CellMultiTable tfidf) {
        this.tfidf = tfidf;
    }

    public CellMultiTable getTfidf() {
        return tfidf;
    }
}
