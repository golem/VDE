package fr.polytech.resmob.vde;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class OneTabActivity extends Activity {

	private ListView mlistView;
	private int test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_list);
		
		// Récupération d'une référence sur la ListView
		this.mlistView = (ListView) findViewById(R.id.listViewPosts);
		this.test = 0;
		// On pourra paramétriser le comportement de l'activité en fonction de l'extra "type"
		Toast.makeText(this, getIntent().getStringExtra("type"), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onResume() {
		// Bon endroit pour faire une requête, je pense...
		// Comme ça elle est faite à chaque changement d'onglet
		// (ou alors on fait une seule requête à la création, puis on met un bouton refresh)
		super.onResume();
		++test;
		Toast.makeText(this, ""+test, Toast.LENGTH_SHORT).show();
		remplirListPosts();
	}
	
	// Remplit la ListView avec des données arbitraire... Pourra prendre un JSONArray
	//ou une chaine en représentant un. 
	private void remplirListPosts() {
		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
		
		for (int i = test; i < test + 10; ++i) {
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("title", "Titre" + i);
			item.put("content", "Contenu");
			item.put("likes", "100");
			itemList.add(item);
		}
		
		ListAdapter adapter = new SimpleAdapter(this, itemList, R.layout.list_item,
				new String[] { "title", "content", "likes" }, new int[] {
                R.id.tvTitle, R.id.tvContent, R.id.tvLikes });
		
		mlistView.setAdapter(adapter);
	}
}
