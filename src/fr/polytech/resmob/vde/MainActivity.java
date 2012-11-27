package fr.polytech.resmob.vde;

import org.apache.http.client.methods.HttpPost;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Activity context;
	
	HttpHandler httpHandler;
	
	// Bouton utilisé pour lancer l'activité d'envoi de nouvel article
	private Button postButton;
	private Button prefButton;
	
	// Préférences
	private String url_server;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		
		httpHandler = new HttpHandler(new String(R.string.url_server_default + "/~frefre∕test.php"));
		
		postButton = (Button) findViewById(R.id.buttonPost);
		postButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Lancement de l'activité "envoi d'article"
				Intent postIntent = new Intent(context, PostActivity.class);
				startActivity(postIntent);
			}
			
		});
		
		prefButton = (Button) findViewById(R.id.buttonPref);
		prefButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Lancement de l'activité "envoi d'article"
				Intent prefIntent = new Intent(context, PreferencesActivity.class);
				startActivity(prefIntent);
			}
			
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		// On récupère les préférences dans le onStart() pour que le changement d'options
		// soit pris en compte quand on revient de l'activité préférences.
		getPrefs();
		Toast.makeText(context, "URL serveur = " + url_server, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	// Permet d'enregistrer les préférences dans les attributs de la classe
	private void getPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		url_server = prefs.getString("serverPref", getResources().getString(R.string.url_server_default));
	}
}
