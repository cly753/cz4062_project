package sg.com.ntu.cz4062.group9.wallpaper.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadWallpaper extends AsyncTask<String, Void, InputStream> {
	public static final String TAG = "DownlaodWallpaper : ";
	
	public static String URL_ADDR = "https://gp1.wac.edgecastcdn.net/806614/photos/photos.500px.net/36059538/0fafc4660756bab2ee6d6804859266f9f4948e02/2048.jpg";

	private Activity activity;

	public DownloadWallpaper(Activity act) {
		this.activity = act;
	}

	protected InputStream doInBackground(String... params) {
		Log.d(TAG, "doInBackground started");
			
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			Log.i(TAG, "Connected.");
		} else {
			Log.i(TAG, "No network connection available.");
			return null;
		}

		if (params.length != 0 && params[0].length() > 20) {
			String tail = params[0].substring(params[0].length() - 4, params[0].length());
			if (tail.equals(".jpg") || tail.equals(".png"))
				URL_ADDR = params[0];
		}
		
		Log.i(TAG, "Addr: " + URL_ADDR);

		try {
			URL url = new URL(URL_ADDR);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoInput(true);
			conn.connect();

			int responseCode = conn.getResponseCode();
			Log.d(TAG, "The response code is: " + responseCode);
			
			InputStream input = conn.getInputStream();
			
			WallpaperManager wm = WallpaperManager.getInstance(activity.getApplicationContext());
			try {
				wm.setStream(input);
			} catch (IOException e) {
				Log.d(TAG, "wm.setStream( ) failed. " + e.toString());
			}
			
			conn.disconnect();
		} catch (Exception e) {
			Log.d(TAG, "a exception in doInBackground. " + e.toString());
		}
		
		Log.i(TAG, "doInBackground finished");
		return null;
	}

	protected void onPostExecute(InputStream result) {
		Log.d(TAG, "onPostExecute started");

		Log.i(TAG, "onPostExecute finished");
	}
}
