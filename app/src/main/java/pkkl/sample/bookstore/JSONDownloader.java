package pkkl.sample.bookstore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class JSONDownloader {

	public static String download(String url) {
		InputStream is = null;
		String result = "";

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget=new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("json_downloader", "Error in http connection " + e.toString());
		}

// Convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
														   is, "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("json_downloader", "Error converting result " + e.toString());
		}

		return result;
	}
}

