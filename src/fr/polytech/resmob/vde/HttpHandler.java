package fr.polytech.resmob.vde;

import org.json.JSONObject;

public class HttpHandler {	
	
	private SendRequest sendRequest;
	
	public HttpHandler() {
		sendRequest = new SendRequest();
	}
	
	public void sendRequest(JSONObject post) {
		sendRequest.execute(post);
	}
}
