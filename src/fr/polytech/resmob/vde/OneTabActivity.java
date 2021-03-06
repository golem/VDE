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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
	private DataHandler dataHandlerLike;
	private String server_domain;
	private boolean show_dialogs;
	private JSONArray posts;
	private Button prevButton;
	private Button nextButton;
	private TextView pageTv;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_list);
		
		this.posts = new JSONArray();
		
		this.context = this;
		
		// Récupération d'une référence sur la ListView
		this.mlistView = (ListView) findViewById(R.id.listViewPosts);
		// On pourra paramétriser le comportement de l'activité en fonction de l'extra "type"
		//Toast.makeText(this, getIntent().getStringExtra("type"), Toast.LENGTH_SHORT).show();
		
		/* DataHandler pour la requête Http */ 
		this.dataHandler = new DataHandler() {
			// On met à jour la liste des posts avec le retour de la requête http
			@Override
			public void onDataSuccess(String s) {
				remplirListPosts(s, false);
				// Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
			}
		};
		
		/* DataHandler pour la requête Http */ 
		this.dataHandlerLike = new DataHandler() {
			// On met à jour la liste des posts avec le retour de la requête http
			@Override
			public void onDataSuccess(String s) {
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
			}
		};
		
		
		this.mlistView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//JSONObject post = posts.getJSONObject(position);
				try {
					if(posts.getJSONObject(position).getString("liked").toString() != "liked") {
						
						posts.getJSONObject(position).put("liked", "liked");
						posts.getJSONObject(position).put("likes", posts.getJSONObject(position).getInt("likes") + 1);
						
						JSONObject req = new JSONObject();
						req.put("domain", server_domain);
						req.put("id", "like");
						req.put("id_elem", posts.getJSONObject(position).getString("id"));
						SendRequest sendReq = new SendRequest(dataHandlerLike, context, show_dialogs);
						sendReq.execute(req);
						remplirListPosts(posts.toString(), true);
					}
					else {
						Toast.makeText(getApplicationContext(), "Likes multiples impossibles", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		
		
		// Ajout des callbacks sur les boutons
		this.prevButton = (Button) findViewById(R.id.buttonPrev);
		this.nextButton = (Button) findViewById(R.id.buttonNext);
		this.pageTv = (TextView) findViewById(R.id.textViewNbPage);
		
		this.page = 0;
		this.prevButton.setEnabled(false);
		pageTv.setText("Page : " + (page+1));
		
		this.prevButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				--page;
				requete();
				// Si on est à la première page, on désactive le bouton "précédent"
				if (page == 0) {
					prevButton.setEnabled(false);
				}
				nextButton.setEnabled(true);
				pageTv.setText("Page : " + (page+1));
			}
		});
		
		this.nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				++page;
				requete();
				// Il faudrait faire quelque chose pour se rendre compte qu'on est à la dernière page...
				if (posts.length() == 0) {
					nextButton.setEnabled(false);
				}
				prevButton.setEnabled(true);
				pageTv.setText("Page : " + (page+1));
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
		
		this.requete();
		
//		++page;
//		Toast.makeText(this, ""+page, Toast.LENGTH_SHORT).show();
//		remplirListPosts("");
	}
	
	// Envoi de la requête, dont le retour remplira automatiquement la ListView
	// Utilise les attributs : page, dataHandler, server_domain
	private void requete() {
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
		
		// Envoi de la requête
		SendRequest sendReq = new SendRequest(dataHandler, context, show_dialogs);
		sendReq.execute(req);
	}
	
	// Remplit la ListView avec des données provenant d'une chaîne représentant un
	// JSONArray
	private void remplirListPosts(String contenu, boolean liked) {
		if (contenu == null) return;
		
		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
		JSONObject post;
		HashMap<String, String> item;
		
		try {
			// Parse la chaîne en tant que tableau JSON
			if(liked == false)
				posts = new JSONArray(contenu);
			int length = posts.length();
			// Itérations sur tous les posts pour les ajouter à la liste
			for (int i = 0; i < length; ++i) {
				if(liked == false)
					posts.getJSONObject(i).put("liked", "no");
					
				post = posts.getJSONObject(i);
				item = new HashMap<String, String>();
				item.put("title", post.getString("title"));
				item.put("content", post.getString("content"));
				item.put("likes", post.getString("likes"));
				item.put("date", post.getString("date"));
				itemList.add(item);
			}
		} catch (JSONException e) {
			Log.e(e.getClass().getName(), e.getMessage(), e);
		}
		
		ListAdapter adapter = new SimpleAdapter(this, itemList, R.layout.list_item,
				new String[] { "title", "content", "likes", "date" }, new int[] {
                R.id.tvTitle, R.id.tvContent, R.id.tvNbLikes, R.id.tvDate });
		
		mlistView.setAdapter(adapter);
	}
	
	// Permet d'enregistrer les préférences dans les attributs de la classe
	private void getPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.server_domain = prefs.getString("serverPref", getResources().getString(R.string.url_server_default));
		this.show_dialogs = prefs.getBoolean("progressPref", getResources().getBoolean(R.bool.show_dialog_default));
	}
}
