package br.ufrn.messages;

import java.util.List;

public class MapData {

	private List<WordCount> words;
	
	public MapData (List<WordCount> words) {
		this.words = words;
	}
	
	public List<WordCount> getWords (){
		return words;
	}

}
