package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import bigdata.reactive.DbActor;
import bigdata.reactive.messages.requests.RequestQuery;
import bigdata.reactive.messages.requests.RequestQueryByTerm;
import models.CellTFidF;
import models.DocumentsFile;
import models.TermSearch;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
//forms
import play.api.data.*;
import play.data.FormFactory;
import play.data.Form;

import java.util.ArrayList;
import java.util.List;

import javax.inject.*;

public class SearchController extends Controller {

	private final Form<TermSearch> form;
	Timeout timeout = new Timeout((FiniteDuration) Duration.create("100 seconds"));
	
	@Inject
	public SearchController (FormFactory formFactory) {
		this.form = formFactory.form(TermSearch.class);
	}
	
	public Result index() {
		return ok(views.html.search.render(form));
	}
	
	public Result getSearchTerm(Http.Request request) {
		ActorSystem system = ActorSystem.create("reactive-tfidf");
		ActorRef db = system.actorOf(DbActor.props());
		
		TermSearch obj = form.bindFromRequest(request).get();
		List<CellTFidF> res = new ArrayList<CellTFidF>();
		
		System.out.println("executando getSearchTerm");
		Future<Object> future = Patterns.ask(db, new RequestQueryByTerm(obj.getTerm()),timeout );
	    try {
	    	List<CellTFidF> result = (List<CellTFidF>) Await.result(future, Duration.create("100 seconds"));
			res = result;
			system.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return ok(views.html.result.render(res));
	}
	
	
}
