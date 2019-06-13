package bigdata.reactive.actors.calculate;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class TFIDFActor extends AbstractActor{

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Props props () {
		return Props.create(TFIDFActor.class);
	}

}
