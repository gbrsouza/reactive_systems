package br.ufrn.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import akka.actor.AbstractActor;
import akka.actor.Props;
import br.ufrn.messages.MapData;
import br.ufrn.messages.WordCount;

public class MapActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(
				String.class, 
				msg -> { getSender()
						 .tell(avaliateExpression(msg),
						 getSelf()); }
				).build();
	}
	
	public MapData avaliateExpression(String m) {
	
		List<WordCount> list = new ArrayList<WordCount>();
		StringTokenizer s = new StringTokenizer(m);
		
		while (s.hasMoreTokens()) {
			String word = s.nextToken().toLowerCase();
			list.add(new WordCount(word, 1));
		}
		
		return new MapData(list);
	
	}
	
	public static Props props () {
		return Props.create(MapActor.class);
	}
}
