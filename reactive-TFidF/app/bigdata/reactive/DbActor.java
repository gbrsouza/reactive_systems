package bigdata.reactive;

import java.util.Iterator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.CellListMessage;
import bigdata.reactive.messages.InverseDocListMessage;
import bigdata.reactive.messages.data.TableData;
import bigdata.reactive.messages.requests.ResultRequest;

public class DbActor extends AbstractActor {

	private Cluster cluster;
	private Session session;
	
	public DbActor() {
		cluster = Cluster.builder().addContactPoint("127.0.0.1")
						 .withCredentials("cassandra", "cassandra")
						 .build();
		Metadata metadata = cluster.getMetadata();
		session = cluster.connect("reactive_tfidf");
		System.out.printf("@@@@@@@Connected to cluster: %s\n",
                metadata.getClusterName());
	}
	
	public String queryUser(Integer i) {
        ResultSet results = session.execute("SELECT * FROM users WHERE id = " + i);
        String line = "";
        for (Iterator<Row> iterator = results.iterator(); iterator.hasNext();) {
            Row row = iterator.next();
            line += row.getString("nome") + "\n";
        }
        return line;
    }
	
	private ResultRequest insertTFidf (TableData list) {
		
		list.getTable().keySet().stream()
			.forEach(msg -> {
				StringBuilder sb = new StringBuilder("INSERT INTO ")
					.append("tfidf ").append("(id, term_name, document_name, value) ")
					.append("VALUES ( uuid(), '").append(msg.getTerm())
					.append("', '").append(msg.getDocument_url())
					.append("', ").append(msg.getValue())
					.append(");");
				
				
				String query = sb.toString();
				session.execute(query);
			});
		return new ResultRequest();
	}
	
	private ResultRequest insertInvDoc (InverseDocListMessage list) {
		
		list.getInv_doc().keySet().stream()
			.forEach(msg -> {
				StringBuilder sb = new StringBuilder("INSERT INTO ")
					.append("inv_doc ").append("(id, term_name, value) ")
					.append("VALUES ( uuid(), '").append(msg)
					.append("', ").append(list.getInv_doc().get(msg))
					.append(");");
				
				
				String query = sb.toString();
				session.execute(query);
			});
		return new ResultRequest();
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(TableData.class, msg -> {
					getSender().tell(insertTFidf(msg), getSelf());
				})
				.match(InverseDocListMessage.class, msg -> {
					getSender().tell(insertInvDoc(msg), getSelf());
				})
				.build();
	}
	
	public static Props props () {
		return Props.create(DbActor.class);
	}

}
