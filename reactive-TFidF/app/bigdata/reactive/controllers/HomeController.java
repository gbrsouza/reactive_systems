package bigdata.reactive.controllers;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import bigdata.reactive.actors.MasterActor;
import bigdata.reactive.messages.BootMessage;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import play.mvc.*;

import javax.inject.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;


public class HomeController extends Controller{
	
	final ActorSystem system = ActorSystem.create("reactive-tfidf");
	final ActorRef master = system.actorOf(MasterActor.props(), "master");
	
	String stop_words = "../reactive-TFidF/files/stopWords.in";
    String urls_documents = "../reactive-TFidF/files/forRead.txt";
	Timeout timeout = new Timeout((FiniteDuration) Duration.create("5 seconds"));
	
	
	public Result index() {
		
		long startTime = System.nanoTime();

        Future<Object> future = Patterns.ask(master, new BootMessage(urls_documents, stop_words),timeout );
        try {
			Await.result(future, Duration.create("10 seconds"));
			system.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        long durationInMs = TimeUnit.NANOSECONDS.toMillis(totalTime);
        return ok("<h1>\"total run time: \" + durationInMs + \" ms\"</h1>");
	}
}
