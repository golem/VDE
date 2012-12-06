package fr.polytech.resmob.vde;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.polytech.resmob.vde.SendRequest.DataHandler;

public class PostActivity extends Activity {
	
	private Activity context;
	private Button submitButton;
	private SendRequest sendRequest;
	private DataHandler dataHandler;
	private String server_domain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.post_layout);
		context = this;
		
		/* DataHandler pour la requête Http */ 
		this.dataHandler = new DataHandler() {
			
			@Override
			public void onDataSuccess(String s) {
				Toast.makeText(context, s, Toast.LENGTH_LONG).show();
			}	
		};
				
		getPrefs();
		
		submitButton = (Button) findViewById(R.id.buttonSoumettre);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Récupération des attributs de l'article (sans vérification)
				String title = ((EditText) findViewById(R.id.editTextTitre)).getText().toString();
				String author = ((EditText) findViewById(R.id.editTextAuteur)).getText().toString();
				String content = ((EditText) findViewById(R.id.editTextPost)).getText().toString();
				
				// Création de l'objet JSON représentant l'article
				JSONObject article = new JSONObject();
				try {
					article.put("title", title);
					article.put("author", author);
					article.put("content", content);
					
					// Ajout des attributs spécifiques à la requête d'insert
					article.put("id", "insert");
					article.put("domain", server_domain);
					//Toast.makeText(context, article.getString("domain").replace("\\", ""), Toast.LENGTH_SHORT).show();
					Toast.makeText(context, article.toString(4).replace("\\", ""), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
				// Envoi d'une requête d'ajout d'un post
				// Il faut re-créer une AsyncTask à chaque fois, a priori...
				sendRequest = new SendRequest(dataHandler, context, true);
				sendRequest.execute(article);
			}		
		});
	}
	
	// Permet d'enregistrer les préférences dans les attributs de la classe
	private void getPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		this.server_domain = prefs.getString("serverPref", getResources().getString(R.string.url_server_default));
	}
}
