package fr.polytech.resmob.vde;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Activity context;
	
	HttpHandler httpHandler;
	
	// Boutons utilisé pour lancer l'activité d'envoi de nouvel article
	private Button postButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		
		httpHandler = new HttpHandler(new String(R.string.url_server + ""));
		
		postButton = (Button) findViewById(R.id.buttonPost);
		postButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Lancement de l'activité "envoi d'article"
				Intent postIntent = new Intent(context, PostActivity.class);
				startActivity(postIntent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
