package controllers;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import bigdata.reactive.DbActor;
import bigdata.reactive.actors.MasterActor;
import bigdata.reactive.messages.BootMessage;
import bigdata.reactive.messages.requests.RequestQuery;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import play.mvc.*;
import play.twirl.api.Html;
import models.*;

import javax.inject.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

// forms
import play.api.data.*;
import play.data.FormFactory;
import play.data.Form;

import javax.inject.Inject;

public class HomeController extends Controller{
	
//	final ActorRef master = system.actorOf(MasterActor.props(), "master");

	
	String stop_words = "../reactive-TFidF/files/stopWords.in";
    //String urls_documents = "	";
	Timeout timeout = new Timeout((FiniteDuration) Duration.create("100 seconds"));
	
	private final Form<DocumentsFile> form;
	
	@Inject
	public HomeController( FormFactory formFactory ) {
		this.form = formFactory.form(DocumentsFile.class);
	}
	
	public Result index() {
		return ok(views.html.index.render(form));
	}

	public Result getResult(Http.Request request) {
		
		ActorSystem system = ActorSystem.create("reactive-tfidf");
		ActorRef db = system.actorOf(DbActor.props());
		
		DocumentsFile obj = form.bindFromRequest(request).get();
		double time = this.execute(obj.getUrl());
		//return ok(views.html.result.render("Success - read and process " + obj.getUrl() + " in " + time + " ms" ));
		List<CellTFidF> res = new ArrayList<CellTFidF>();
		
		Future<Object> future = Patterns.ask(db, new RequestQuery(),timeout );
	    try {
	    	List<CellTFidF> result = (List<CellTFidF>) Await.result(future, Duration.create("100 seconds"));
			res = result;
			system.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	    return ok(views.html.result.render(res));
	}
	
	private double execute (String urls_documents) {
		ActorSystem system = ActorSystem.create("reactive-tfidf");
		ActorRef master = system.actorOf(MasterActor.props(), "master");
		
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
	    return durationInMs;
	}

	
	
}
