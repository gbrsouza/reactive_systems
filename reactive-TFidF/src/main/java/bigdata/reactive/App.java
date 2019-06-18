package bigdata.reactive;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import bigdata.reactive.actors.MasterActor;
import bigdata.reactive.messages.BootMessage;
import bigdata.reactive.messages.StopWordData;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
	
	
	private static void execute () {
		String stop_words = "../reactive-TFidF/files/stopWords.in";
	    String urls_documents = "../reactive-TFidF/files/forRead.txt";
		Timeout timeout = new Timeout((FiniteDuration) Duration.create("5 seconds"));
        System.out.println( "Hello World!" );
		
		ActorSystem system = ActorSystem.create("tfIdfSystem");
	    ActorRef master = system.actorOf(MasterActor.props(), "master");
		
		long startTime = System.nanoTime();

        Future<Object> future = Patterns.ask(master, new BootMessage(urls_documents, stop_words),timeout );
        try {
			Object result =  Await.result(future, Duration.create("5 seconds"));
			system.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        long durationInMs = TimeUnit.NANOSECONDS.toMillis(totalTime);
        System.out.println("total run time: " + durationInMs + " ms");
        
	}
	
    public static void main( String[] args )
    {
    	for (int i = 0; i < 30; i++) {
    		execute();
    	}
    }
}
