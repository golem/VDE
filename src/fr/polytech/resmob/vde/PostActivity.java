package fr.polytech.resmob.vde;

import java.security.DomainCombiner;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostActivity extends Activity {
	
	private Activity context;
	private Button submitButton;
	private HttpHandler httpHandler;
	
	private String server_domain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_layout);
		
		context = this;
		
		httpHandler = new HttpHandler(context);
		
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
					//article.put("author", author);
					article.put("content", content);
					
					// Ajout des attributs spécifiques à la requête d'insert
					article.put("id", "insert");
					article.put("domain", server_domain);
					
					Toast.makeText(context, article.toString(4), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
				
				// Envoi d'une requête d'ajout d'un post
				httpHandler.sendRequest(article);
//				try {
//					JSONObject j = new JSONObject("{\"id\":\"insert\",\"title\":\"test\",\"content\":\"je suis un post de test\"}");
//					httpHandler.sendRequest(j);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				/*while(httpHandler.getS().equals(new String("test"))) {
					
				}*/
				
			}		
		});
	}
	
	// Permet d'enregistrer les préférences dans les attributs de la classe
	private void getPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		this.server_domain = prefs.getString("serverPref", getResources().getString(R.string.url_server_default));
	}
}
