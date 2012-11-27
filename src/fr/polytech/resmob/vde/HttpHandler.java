package fr.polytech.resmob.vde;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.widget.Toast;

public class HttpHandler {
	
	private HttpClient httpclient;
	private HttpPost httppost;
	
	public HttpHandler(String url) {
		
		this.httpclient = new DefaultHttpClient();
		this.httppost = new HttpPost(url);
	}
	
	
	/* A réfléchir */
	
	/* Requête pour récupérer le nombre de posts les plus aimés à partir du
	 * nombre de likes en paramètre, si on envoie 0 on récupère les plus aimés,
	 * si on envoie un nombre de like on récupère les plus aimés en dessous
	 * de ce nombre de like, de plus on ne récupère pas les ids en paramètre */
	public void query(int likes, int number, int pageNumber) {
		
	}
	
	/* Requête pour récupérer le nombre de posts les plus récents jusqu'à
	 * de la date en paramètre, de plus on ne récupère pas les ids en paramètre */
	public void query(Date fromDate, int number, int pageNumber) {
		
	}
	
	/* Requête pour récupérer un certain nombre de posts aléatoirement */
	public void query(int number) {
		
	}
	
	public HttpResponse insert(String title, String author, String content) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("id", "insert"));
        nameValuePairs.add(new BasicNameValuePair("title", title));
        //nameValuePairs.add(new BasicNameValuePair("author", author));
        nameValuePairs.add(new BasicNameValuePair("content", content));
        try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			return response;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
}
