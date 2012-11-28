package fr.polytech.resmob.vde;

import org.json.JSONObject;

public class HttpHandler {	
	
	private SendRequest sendRequest;
	
	public HttpHandler() {
		sendRequest = new SendRequest();
	}
	
	/* Ceci est un commentaire pour faire un test */
	public void sendRequest(JSONObject post) {
		sendRequest.execute(post);
	}
}
