package fr.polytech.resmob.vde;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import fr.polytech.resmob.vde.SendRequest.DataHandler;

public class OneTabActivity extends Activity {

	private ListView mlistView;
	private int page;
	private DataHandler dataHandler;
	private String server_domain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_list);
		
		// Récupération d'une référence sur la ListView
		this.mlistView = (ListView) findViewById(R.id.listViewPosts);
		this.page = 0;
		// On pourra paramétriser le comportement de l'activité en fonction de l'extra "type"
		Toast.makeText(this, getIntent().getStringExtra("type"), Toast.LENGTH_SHORT).show();
		
		/* DataHandler pour la requête Http */ 
		this.dataHandler = new DataHandler() {
			// On met à jour la liste des posts avec le retour de la requête http
			@Override
			public void onDataSuccess(String s) {
				remplirListPosts(s);
				// Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
			}
		};
		
		
		this.mlistView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    Toast.makeText(getApplicationContext(), "" + position,Toast.LENGTH_SHORT).show();
			}
		});
		
		// Récupération des préférences
		getPrefs();
	}
	
	@Override
	protected void onResume() {
		// Bon endroit pour faire une requête, je pense...
		// Comme ça elle est faite à chaque changement d'onglet
		// (ou alors on fait une seule requête à la création, puis on met un bouton refresh)
		super.onResume();
		
		// Création de la requête
		JSONObject req;
		req = new JSONObject();
		try {
			req.put("domain", this.server_domain);
			req.put("id", getIntent().getStringExtra("type"));
			req.put("page", this.page);
		} catch (JSONException e) {
			Log.e(e.getClass().getName(), e.getMessage(), e);
		}
		
		SendRequest sendReq = new SendRequest(dataHandler);
		sendReq.execute(req);
		
//		++page;
//		Toast.makeText(this, ""+page, Toast.LENGTH_SHORT).show();
//		remplirListPosts("");
	}
	
	// Remplit la ListView avec des données arbitraire... Pourra prendre un JSONArray
	//ou une chaine en représentant un. 
	private void remplirListPosts(String contenu) {
		if (contenu == null) return;
		
		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
		JSONArray posts;
		JSONObject post;
		HashMap<String, String> item;
		
		try {
			// Parse la chaîne en tant que tableau JSON
			posts = new JSONArray(contenu);
			
			// Itérations sur tous les posts pour les ajouter à la liste
			for (int i = 0; i < posts.length(); ++i) {
				post = posts.getJSONObject(i);
				item = new HashMap<String, String>();
				item.put("title", post.getString("title"));
				item.put("content", post.getString("content"));
				item.put("likes", post.getString("likes"));
				itemList.add(item);
			}
		} catch (JSONException e) {
			Log.e(e.getClass().getName(), e.getMessage(), e);
		}
		
		ListAdapter adapter = new SimpleAdapter(this, itemList, R.layout.list_item,
				new String[] { "title", "content", "likes" }, new int[] {
                R.id.tvTitle, R.id.tvContent, R.id.tvLikes });
		
		mlistView.setAdapter(adapter);
	}
	
	// Permet d'enregistrer les préférences dans les attributs de la classe
	private void getPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.server_domain = prefs.getString("serverPref", getResources().getString(R.string.url_server_default));
	}
}
