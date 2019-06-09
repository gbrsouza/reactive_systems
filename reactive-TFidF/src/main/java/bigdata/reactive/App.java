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
 
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//Timeout timeout = new Timeout((FiniteDuration) Duration.create("5 seconds"));
        System.out.println( "Hello World!" );
        
        ActorSystem system = ActorSystem.create("tfIdfSystem");
        ActorRef master = system.actorOf(MasterActor.props(), "master");
        
        String stop_words = "../reactive-TFidF/files/stopWords.in";
        String urls_documents = "../reactive-TFidF/files/forRead.txt";
        
        master.tell( new BootMessage(urls_documents, stop_words), master);
        
//        Future<Object> stop_words_future = Patterns.ask(master, new StopWordsUrl( stop_words ),timeout );
        
//        String urls_documents = "../reactive-TFidF/files/forRead.txt";
//        Future<Object> urls_documents_future = Patterns.ask(master, new UrlDocuments(urls_documents) ,timeout );
        
        
//        try {
//			StopWordData result = (StopWordData) Await.result(stop_words_future, Duration.create("5 seconds"));
//			System.out.println(result.test());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }
}
