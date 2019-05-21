package br.ufrn.actors;

import java.util.HashMap;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import br.ufrn.messages.MapData;
import br.ufrn.messages.ReduceData;
import br.ufrn.messages.WordCount;

public class ReduceActor extends AbstractActor{

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(MapData.class, msg -> getSender().tell(reduce(msg.getWords()), getSelf()))
				.build();
	}
	
	private ReduceData reduce ( List<WordCount> list ) {
		
		HashMap <String, Integer> reduceMap = new HashMap<String, Integer>();
		Integer value;
		
		for ( WordCount wc : list ) {
			if ( reduceMap.containsKey( wc.getWord()) ) {
				value = reduceMap.get(wc.getWord());
				value++;
				reduceMap.put(wc.getWord(), value);
			}else {
				reduceMap.put(wc.getWord(), 1);
			}
		}
		
		return new ReduceData(reduceMap);
		
	}
	
	public static Props props () {
		return Props.create(ReduceActor.class);
	}

	
	
	
}
