package bigdata.reactive.messages.requests;

import bigdata.reactive.CellMultiTable;
import bigdata.reactive.messages.data.DocumentData;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RequestCalculatorTFIDFMessage {

    private final Map<String, Double> inv_doc_list;
    private final Map<CellMultiTable, Double> term_frequency_list;
    private final List<DocumentData> document_list;
    private final Map<String, Integer> terms_list;

    public RequestCalculatorTFIDFMessage(Map<String, Double> inv_doc_list,
                                         Map<CellMultiTable, Double> term_frequency_list,
                                         List<DocumentData> document_list,
                                         Map<String, Integer> terms_list)
    {
        this.inv_doc_list = Collections.unmodifiableMap(inv_doc_list);
        this.term_frequency_list = Collections.unmodifiableMap(term_frequency_list);
        this.document_list = Collections.unmodifiableList(document_list);
        this.terms_list = Collections.unmodifiableMap(terms_list);
    }

    public Map<String, Double> getInv_doc_list() {
        return inv_doc_list;
    }

    public Map<CellMultiTable, Double> getTerm_frequency_list() {
        return term_frequency_list;
    }

    public List<DocumentData> getDocument_list() {
        return document_list;
    }

    public Map<String, Integer> getTerms_list() {
        return terms_list;
    }
}
