package fr.polytech.resmob.vde;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import fr.polytech.resmob.vde.SendRequest.DataHandler;

public class MainActivity extends Activity {

	private Activity context;
	
	// Bouton utilisé pour lancer l'activité d'envoi de nouvel article
	private Button listButton;
	private Button postButton;
	private Button prefButton;
	
	// Préférences
	private String url_server;
	
	// DataHandler
	private DataHandler dataHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		context = this;
		
		listButton = (Button) findViewById(R.id.buttonList);
		listButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Lancement de l'activité "envoi d'article"
				Intent listIntent = new Intent(context, TabListActivity.class);
				startActivity(listIntent);
			}
			
		});
		
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
				// Lancement de l'activité "Préférences"
				Intent prefIntent = new Intent(context, PreferencesActivity.class);
				startActivity(prefIntent);
			}
			
		});
		
		/* DataHandler pour la requête Http */ 
		this.dataHandler = new DataHandler() {
			// On met à jour la liste des posts avec le retour de la requête http
			@Override
			public void onDataSuccess(String s) {
				TextView tv = (TextView) findViewById(R.id.tvLastPost);				
				JSONArray posts;
				try {
					if (s == null) return;
					posts = new JSONArray(s);
					tv.setText("\"" + posts.getJSONObject(0).getString("content") + "\"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		// On récupère les préférences dans le onStart() pour que le changement d'options
		// soit pris en compte quand on revient de l'activité préférences.
		getPrefs();
		Toast.makeText(context, "URL serveur = " + url_server, Toast.LENGTH_SHORT).show();
		this.requete();
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			// Lancement de l'activité "Préférences"
			Intent prefIntent = new Intent(context, PreferencesActivity.class);
			startActivity(prefIntent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void requete() {
		// Création de la requête
		JSONObject req;
		req = new JSONObject();
		try {
			req.put("domain", url_server);
			req.put("id", "date");
			req.put("page", 0);
		} catch (JSONException e) {
			Log.e(e.getClass().getName(), e.getMessage(), e);
		}
		
		// Envoi de la requête
		SendRequest sendReq = new SendRequest(dataHandler, context, false);
		sendReq.execute(req);
	}
}
