package br.ufrn.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import br.ufrn.messages.MapData;
import br.ufrn.messages.ReduceData;
import br.ufrn.messages.Result;

public class MasterActor extends AbstractActor{

	ActorRef mapActor = getContext().actorOf(MapActor.props(), "map");
	ActorRef reduceActor = getContext().actorOf(ReduceActor.props(), "reduce");
	ActorRef aggregateActor = getContext().actorOf(AggregateActor.props(), "aggregate");
	
	@Override
	public Receive createReceive() {
		
		return receiveBuilder()
				.match(MapData.class, msg -> {reduceActor.tell(msg, getSelf());})
				.match(ReduceData.class, msg -> {aggregateActor.tell(msg, getSelf());})
				.match(String.class, msg -> {mapActor.tell(msg, getSelf());})
				.match(Result.class, msg -> {aggregateActor.forward(msg, getContext());})
				.build();
		
	}
	
	public static Props props () {
		return Props.create(MasterActor.class);
	}

}
