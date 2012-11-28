package fr.polytech.resmob.vde;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;

public class SendRequest extends AsyncTask<JSONObject, Integer, Long> {

	private HttpClient httpclient;
	private HttpPost httppost;
	
	public SendRequest() {
		this.httpclient = new DefaultHttpClient();
		this.httppost = new HttpPost("http://192.168.1.2/~frefre/test.php");
	}

	@Override
	protected Long doInBackground(JSONObject... params) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("request", params[0].toString()));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpclient.execute(httppost);
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
}
