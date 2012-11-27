package fr.polytech.resmob.vde;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PostActivity extends Activity {
	
	private Activity context;
	private Button submitButton;
	private HttpHandler httpHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_layout);
		
		context = this;
		
		 httpHandler = new HttpHandler("test");
		// httpHandler = new HttpHandler(new String("http://192.168.1.2/~frefre∕test.php"));
		
		submitButton = (Button) findViewById(R.id.buttonSoumettre);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Envoi d'une requête d'ajout d'un post
				httpHandler.insert("test", "null", "j'ai essayé");
			}		
		});
	}
}
