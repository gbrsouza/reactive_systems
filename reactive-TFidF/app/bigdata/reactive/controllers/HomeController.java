package bigdata.reactive.controllers;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import bigdata.reactive.actors.MasterActor;
import scala.compat.java8.FutureConverters;
import play.mvc.*;

import javax.inject.*;
import java.util.concurrent.CompletionStage;


public class HomeController extends Controller{
	
	final ActorSystem system = ActorSystem.create("reactive-tfidf");
	final ActorRef master = system.actorOf(MasterActor.props(), "master");
	
	public Result index() {
		return ok(	views.html.index.render());
	}
}
