package fr.polytech.resmob.vde;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PostActivity extends Activity {
	
	private Activity context;
	private Button submitButton;
	private HttpHandler httpHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_layout);
		
		context = this;
		
		httpHandler = new HttpHandler(context);
		
		submitButton = (Button) findViewById(R.id.buttonSoumettre);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Envoi d'une requête d'ajout d'un post
				
				try {
					JSONObject j = new JSONObject("{\"id\":\"insert\",\"title\":\"test\",\"content\":\"je suis un post de test\"}");
					httpHandler.sendRequest(j);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*while(httpHandler.getS().equals(new String("test"))) {
					
				}*/
				
			}		
		});
	}
}
