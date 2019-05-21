package br.ufrn.actors;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.Props;
import br.ufrn.messages.ReduceData;
import br.ufrn.messages.Result;

public class AggregateActor extends AbstractActor {

	private Map<String, Integer> map = new HashMap<>();
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(ReduceData.class, msg -> {populate(msg.getMessage());})
				.match(Result.class, msg -> getSender().tell(map.toString(), getSelf()) )
				.build();
	}
	
	private void populate ( Map<String, Integer> reduceList )
	{
		Integer count = 0;
		for ( String s : reduceList.keySet() ) {
			if ( this.map.containsKey(s) ) {
				count = reduceList.get(s) + this.map.get(s);
				map.put(s, count);
			} else {
				map.put(s, reduceList.get(s));
			}
		}
	}
	
	public Props props () {
		return Props.create(AggregateActor.class);
	}
	
}
