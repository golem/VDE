package fr.polytech.resmob.vde;

import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

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
	public void query(int likes, int number, int[] ids) {
		
	}
	
	/* Requête pour récupérer le nombre de posts les plus récents jusqu'à
	 * de la date en paramètre, de plus on ne récupère pas les ids en paramètre */
	public void query(Date fromDate, int number, int[] ids) {
		
	}
	
	/* Requête pour récupérer un certain nombre de posts aléatoirement */
	public void query(int number) {
		
	}
}
