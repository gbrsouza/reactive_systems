package bigdata.reactive.messages.data;

public class TFIDFDataMessage {

    private final TableData table;

    public TFIDFDataMessage(TableData table) {
        this.table = table;
    }

    public TableData getTable() {
        return table;
    }
}
