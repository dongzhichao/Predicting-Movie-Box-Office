package louzong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Crawler {
	
	private String result;
	private URLConnection urlConnection = null;
	private BufferedReader bReader = null;
	private URL realUrl = null;
	
	private void connectToWebsite(String url){
		try {
			realUrl = new URL(url);
			urlConnection = realUrl.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void getBufferedReader(String url){
		connectToWebsite(url);
		try {
			bReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void getContent(String url){
		getBufferedReader(url);
		result = null;
		String line=null;
		try {
			line = bReader.readLine();
			while (line!=null) {
				result+=line;
				line = bReader.readLine();
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public URLConnection getUrlConnection() {
		return urlConnection;
	}

	public void setUrlConnection(URLConnection urlConnection) {
		this.urlConnection = urlConnection;
	}

	public BufferedReader getbReader() {
		return bReader;
	}

	public void setbReader(BufferedReader bReader) {
		this.bReader = bReader;
	}

	public URL getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(URL realUrl) {
		this.realUrl = realUrl;
	}
	
}
