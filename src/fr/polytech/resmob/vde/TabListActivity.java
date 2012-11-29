package fr.polytech.resmob.vde;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabListActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		context = this;
		
		Intent intent = new Intent(this, OneTabActivity.class);
		tabHost = getTabHost();
		tabSpec = tabHost.newTabSpec("first").setIndicator("Récents").setContent(intent);
		tabHost.addTab(tabSpec);
		tabSpec = tabHost.newTabSpec("second").setIndicator("Top").setContent(intent);
		tabHost.addTab(tabSpec);
		tabSpec = tabHost.newTabSpec("third").setIndicator("Aléatoires").setContent(intent);
		tabHost.addTab(tabSpec);
	}
}