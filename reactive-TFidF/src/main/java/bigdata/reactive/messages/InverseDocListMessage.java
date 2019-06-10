package bigdata.reactive.messages;

import java.util.HashMap;

public class InverseDocListMessage {

	private final HashMap<String, Double> inv_doc;

	public InverseDocListMessage(HashMap<String, Double> inv_doc) {
		super();
		this.inv_doc = inv_doc;
	}

	public HashMap<String, Double> getInv_doc() {
		return inv_doc;
	}
	
}
