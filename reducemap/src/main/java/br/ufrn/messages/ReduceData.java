package br.ufrn.messages;

import java.util.HashMap;

public class ReduceData {

	private HashMap<String, Integer> message;
	
	public ReduceData ( HashMap<String, Integer> message ) {
		this.message = message;
	}
	
	public HashMap<String, Integer> getMessage (){ 
		return message;
	}
	
}
