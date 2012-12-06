package fr.polytech.resmob.vde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class SendRequest extends AsyncTask<JSONObject, Integer, String> {

	private HttpClient httpclient;
	private HttpPost httppost;
	private HttpResponse httpResponse;
	DataHandler dataHandler;
	private String s;
	
	private final ProgressDialog dialog;
	
	public SendRequest(DataHandler dataHandler, Activity context, boolean showDialog) {
		//this.answer = new String();
		this.dataHandler = dataHandler;
		this.httpclient = new DefaultHttpClient();
		//this.httppost = new HttpPost("http://86.198.34.101/~frefre/test.php");
		
		// On affiche éventuellement un progressDialog
		if (showDialog) {
			this.dialog = new ProgressDialog(context);
			// TODO: On pourrait passer ce message en argument par exemple...
			// (et aussi un booléen pour dire si on veut le progressdialog tout court)
			this.dialog.setMessage("Requête en cours...");
			this.dialog.show();
		}
		else this.dialog = null;
	}

	@Override
	protected String doInBackground(JSONObject... params) {
		String server_url = "";
		try {
			server_url = "http://" + params[0].getString("domain") + "/~frefre/test.php";
			params[0].remove("domain");
		} catch (JSONException e) {
			Log.e(e.getClass().getName(), e.getMessage(), e);
		}
		
		this.httppost = new HttpPost(server_url);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("request", params[0].toString()));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpResponse = httpclient.execute(httppost);
			
			s = returnDataFromResponse(httpResponse);			
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String returnDataFromResponse(HttpResponse httpResponse) throws IllegalStateException, IOException {
	
		String s = "";
		String line = "";
		BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		while ((line = rd.readLine()) != null) { s += line; }
		return s;
	}
	
	@Override
    protected void onPostExecute(String s) {
		dataHandler.onDataSuccess(s);
		if (this.dialog != null)
			this.dialog.dismiss();
    }
	
	public static interface DataHandler {
		public void onDataSuccess(String s);
	}
}
