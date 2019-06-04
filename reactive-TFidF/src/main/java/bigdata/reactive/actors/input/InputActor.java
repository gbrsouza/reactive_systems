package bigdata.reactive.actors.input;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class InputActor extends AbstractActor{

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * method to return a Props
	 * @return return a this class Props 
	 */
	public static Props props () {
		return Props.create(InputActor.class);
	}

}
