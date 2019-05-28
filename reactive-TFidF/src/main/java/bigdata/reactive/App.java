package bigdata.reactive;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import bigdata.reactive.actors.MasterActor;
import bigdata.reactive.messages.StopWordData;
import bigdata.reactive.messages.StopWordsUrl;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Timeout timeout = new Timeout((FiniteDuration) Duration.create("5 seconds"));
        System.out.println( "Hello World!" );
        
        ActorSystem system = ActorSystem.create("tfIdfSystem");
        ActorRef master = system.actorOf(MasterActor.props(), "master");
        
        String filename = "/home/gabriel/Códigos/java/TF-iDF/TFidF/archive/stopWords.in";
        Future<Object> future = Patterns.ask(master, new StopWordsUrl( filename ),timeout );
        try {
			StopWordData result = (StopWordData) Await.result(future, Duration.create("5 seconds"));
			System.out.println(result.test());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
