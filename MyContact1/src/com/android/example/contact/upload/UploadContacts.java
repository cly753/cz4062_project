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
import android.widget.Toast;

public class UploadContacts extends AsyncTask<String, Void, String> {
	String TAG = "UploadContacts : ";

	private String URL_ADDR = "http://10.0.3.2:8080/";
	// private String URL_ADDR = "http://www.google.com.sg/";
	private String URL_PARA = "result=no+data+come";

	private Activity activity;

	public UploadContacts(Activity activity, String addr) {
		Log.d(TAG, "creating. ");
		this.activity = activity;
		this.URL_ADDR = addr;
		Log.i(TAG, "created. ");
	}

	@Override
	protected String doInBackground(String... toUpload) {
		Log.d(TAG, "doInBackground started");
		
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			Log.i(TAG, "Connected.");
		} else {
			Log.i(TAG, "No network connection available.");
			return "";
		}

		if (toUpload.length != 0)
			URL_PARA = toUpload[0];
		else
			Log.i(TAG, "toUpload.length == 0");

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
			Log.i(TAG, "The response code is: " + responseCode);
						
			conn.disconnect();
			return "";
		} catch (Exception e) {
			Log.i(TAG, "a exception in doInBackground. " + e.toString());
		}
		
		Log.i(TAG, "doInBackground finished");
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
	}
}
