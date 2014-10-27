package com.android.example.contact.upload;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class UploadContacts extends AsyncTask<String, Void, String> {
	String TAG = "UploadContacts : ";

	private String URL_ADDR = "http://10.0.3.2:8080/";
	// private String URL_ADDR = "http://www.google.com.sg/";
	private String URL_PARA = "result=no+data+come";

	private Activity activity;

	public UploadContacts(Activity activity) {
		this.activity = activity;
		Log.d(TAG, "created.");
	}

	@Override
	protected String doInBackground(String... toUpload) {
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			Log.d(TAG, "Connected.");
		} else {
			Log.d(TAG, "No network connection available.");
			return "";
		}

		if (toUpload.length != 0)
			URL_PARA = toUpload[0];

		try {
			URL url = new URL(URL_ADDR);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// add reuqest header
			conn.setRequestMethod("POST");

			// Send post request
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(URL_PARA);
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
			Log.d(TAG, "The response code is: " + responseCode);
			
			conn.disconnect();
			return "";
		} catch (Exception e) {
			Log.d(TAG, "a exception in doInBackground.");
		}
		Log.d(TAG, "doInBackground finished");
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
	}
}
