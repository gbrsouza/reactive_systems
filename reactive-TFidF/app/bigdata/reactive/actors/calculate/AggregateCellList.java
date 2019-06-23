package bigdata.reactive.actors.calculate;

import java.util.HashMap;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.CellMultiTable;
import bigdata.reactive.messages.CellListMessage;
import bigdata.reactive.messages.data.TableData;
import bigdata.reactive.messages.requests.ResultRequest;

public class AggregateCellList extends AbstractActor {

	private HashMap<CellMultiTable, Double> table = new HashMap<>();

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(CellListMessage.class, msg->{
					msg.getCells().stream()
						.forEach( cell -> 
						{ table.put(cell, cell.getValue()); });
				})
				.match(ResultRequest.class, msg->{
					getSender().tell(new TableData(table), getSelf());
				}).build();
	}
	
	public static Props props () {
		return Props.create(AggregateCellList.class);
	}
	
	
	
}
