package fr.polytech.resmob.vde;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OneTabActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_list);
		
		// On pourra paramétriser le comportement de l'activité en fonction de l'extra "type"
		TextView tv = (TextView) findViewById(R.id.monTextView);
		tv.setText(getIntent().getStringExtra("type"));
	}
}
