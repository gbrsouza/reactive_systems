package bigdata.reactive.actors.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import akka.actor.AbstractActor;
import akka.actor.Props;
import bigdata.reactive.messages.data.StopWordData;

public class StopWordActor extends AbstractActor{

	@Override
	public Receive createReceive() {

		return receiveBuilder()
				.match( String.class, msg -> {
					getSender().tell(readStopWords(msg), getSelf());
				}).build();
		
	}
	
	/**
	 * Read all stop words from a file
	 * @param filename  the filename
	 * @return The map of stop words
	 */
	private StopWordData 
	readStopWords ( String filename ) 
	{
		
		SparkConf conf = new SparkConf().setMaster("spark://Inspiron:7077")
	            .setAppName("StopWords");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		
		// carrega os dados das stop words
		JavaRDD<String> linhas = ctx.textFile(filename);
		
		List<String> resultados = linhas.collect();
		// create a map for stop words
		HashMap<String, Integer> stopWords = 
				new HashMap<String, Integer>();
		int id = 0;
		for ( String s : resultados) {
			stopWords.put(s, id);
			id++;
		}
		
		ctx.close();
		return new StopWordData( stopWords );
		
		
		// read all stop words in the file
//		try {
//			
//			FileReader file = new FileReader( filename );
//			BufferedReader reader = new BufferedReader(file);
//			
//			String line = reader.readLine();
//			Integer id = 1;
//			
//			while (line != null) {
//				stopWords.put(line, id);
//				line = reader.readLine();
//				id++;
//			}
//			file.close();
//			
//			// return the message 
//			return new StopWordData( stopWords ); 
//			
//		}catch (Exception e) {
//			System.err.printf("Erro ao abrir arquivo: %s.\n", e.getMessage());
//		}
//		
//		// return null if occurred an error
//		return null;
	}

	/**
	 * method to return a Props
	 * @return return a this class Props 
	 */
	public static Props props () {
		return Props.create(StopWordActor.class);
	}
	
}
