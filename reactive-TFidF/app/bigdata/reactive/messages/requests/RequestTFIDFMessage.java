package bigdata.reactive.messages.requests;

import bigdata.reactive.CellMultiTable;
import bigdata.reactive.messages.data.DocumentData;

import java.util.List;
import java.util.Map;

public class RequestTFIDFMessage {

    private final String term;
    private final Map<CellMultiTable, Double> term_frequency_list;
    private final List<DocumentData> document_list;
    private final Map<String, Double> inv_doc_list;

    public RequestTFIDFMessage(String term,
                               Map<CellMultiTable, Double> term_frequency_list,
                               List<DocumentData> document_list,
                               Map<String, Double> inv_doc_list)
    {
        this.term = term;
        this.term_frequency_list = term_frequency_list;
        this.document_list = document_list;
        this.inv_doc_list = inv_doc_list;
    }

    public String getTerm() {return term;}

    public Map<CellMultiTable, Double> getTerm_frequency_list() {
        return term_frequency_list;
    }

    public List<DocumentData> getDocument_list() {
        return document_list;
    }

    public Map<String, Double> getInv_doc_list() {
        return inv_doc_list;
    }
}
