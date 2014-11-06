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

public class DownloadWallpaper extends AsyncTask<String, Void, Void> {	
	/*
	 * a default wallpaper URL
	*/
	public static String URL_ADDR = "https://gp1.wac.edgecastcdn.net/806614/photos/photos.500px.net/36059538/0fafc4660756bab2ee6d6804859266f9f4948e02/2048.jpg";

	private Activity activity;

	public DownloadWallpaper(Activity act) {
		this.activity = act;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		/*
		 * check whether the app has internet connection,
		 * if not, exit
		*/
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo == null || !networkInfo.isConnected())
			return null;

		/*
		 * a simple check whether the input URL is valid
		*/
		if (params.length != 0 && params[0].length() > 20) {
			String tail = params[0].substring(params[0].length() - 4, params[0].length());
			if (tail.equals(".jpg") || tail.equals(".png"))
				URL_ADDR = params[0];
		}
		
		try {
			
			/*
			 * connect to the wallpaper URL
			*/
			URL url = new URL(URL_ADDR);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoInput(true);
			conn.connect();

			int responseCode = conn.getResponseCode();
			
			/*
			 * set the wallpaper to the data received
			*/
			InputStream input = conn.getInputStream();
			WallpaperManager wm = WallpaperManager.getInstance(activity.getApplicationContext());
			try {
				wm.setStream(input);
			} catch (IOException e) {
			}
			
			conn.disconnect();
		} catch (Exception e) {
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
	}
}
