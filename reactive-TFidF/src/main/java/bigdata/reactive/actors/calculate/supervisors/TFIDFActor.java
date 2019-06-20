package bigdata.reactive.actors.calculate.supervisors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.CellMultiTable;
import bigdata.reactive.messages.CellListMessage;
import bigdata.reactive.messages.data.DocumentData;
import bigdata.reactive.messages.requests.RequestTFIDFMessage;

import java.util.ArrayList;
import java.util.List;

public class TFIDFActor extends AbstractActor{

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestTFIDFMessage.class, msg ->{
                    getSender().tell(calculate(msg), getSelf());
                })
                .build();
    }

    private CellListMessage calculate ( RequestTFIDFMessage msg )
    {
        List<CellMultiTable> table = new ArrayList<>();
        List<DocumentData> docs = msg.getDocument_list();

        double value = 0.0;
        double tfValue = 0.0;

        for ( DocumentData d : docs ){
            CellMultiTable cell = new CellMultiTable(d.get_url(), msg.getTerm(), 0.00);
            if ( msg.getTerm_frequency_list().containsKey( cell ) ) {
                tfValue = msg.getTerm_frequency_list().get(cell);
                value = tfValue * msg.getInv_doc_list().get(msg.getTerm());
                cell.setValue(value);
                table.add(cell);
            }
        }

        return new CellListMessage(table);
    }


    public static Props props () {
        return Props.create(TFIDFActor.class);
    }

}

