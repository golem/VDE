package fr.polytech.resmob.vde;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabListActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list);
		context = this;
		
		Intent intentRecents = new Intent(this, OneTabActivity.class);
		intentRecents.putExtra("type", "date");
		Intent intentTop = new Intent(this, OneTabActivity.class);
		intentTop.putExtra("type", "top");
		Intent intentRandom = new Intent(this, OneTabActivity.class);
		intentRandom.putExtra("type", "random");
		tabHost = getTabHost();
		tabSpec = tabHost.newTabSpec("first").setIndicator("Récents").setContent(intentRecents);
		tabHost.addTab(tabSpec);
		tabSpec = tabHost.newTabSpec("second").setIndicator("Top").setContent(intentTop);
		tabHost.addTab(tabSpec);
		tabSpec = tabHost.newTabSpec("third").setIndicator("Aléatoires").setContent(intentRandom);
		tabHost.addTab(tabSpec);
	}
}