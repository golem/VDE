package fr.polytech.resmob.vde;

import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;
import fr.polytech.resmob.vde.SendRequest.DataHandler;

public class HttpHandler {	
	
	private SendRequest sendRequest;
	private DataHandler dataHandler;
	private String s;
	private Activity context;
	
	public HttpHandler(Activity context) {
		
		this.dataHandler = new DataHandler() {
			
			@Override
			public void onDataSuccess(String s) {
				
				setS(s);
				toast();
			}	
		};
		s = "test";
		this.context = context;
		sendRequest = new SendRequest(dataHandler);
	}
	
	/* Ceci est un commentaire pour faire un test 
	 * et sa continuité */
	public void sendRequest(JSONObject post) {
		sendRequest.execute(post);
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
	
	public void toast() {
		Toast.makeText(this.context, this.s, Toast.LENGTH_LONG).show();
	}
}
