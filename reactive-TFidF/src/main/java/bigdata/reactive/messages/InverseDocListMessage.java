package bigdata.reactive.messages;

import java.util.Collections;
import java.util.Map;

public class InverseDocListMessage {

	private final Map<String, Double> inv_doc;

	public InverseDocListMessage(Map<String, Double> inv_doc) {
		super();
		this.inv_doc = Collections.unmodifiableMap(inv_doc);
	}

	public Map<String, Double> getInv_doc() {
		return inv_doc;
	}
	
}
