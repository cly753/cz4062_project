package sg.com.ntu.cz4062.group9.wallpaper.receiver;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sg.com.ntu.cz4062.group9.wallpaper.MyWallpaper;
import sg.com.ntu.cz4062.group9.wallpaper.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	public static final String TAG = "MyReceiver : ";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive started.");

		Toast.makeText(context, "~ MyWallpaper MyReceiver ~", Toast.LENGTH_LONG)
				.show();

		String source = intent.getStringExtra("SOURCE");
		if (source == null
				|| !source.equals("sg.com.ntu.cz4062.group9.contact")) {
			Log.d(TAG, "INVALIDE SOURCE.");
			return ;
		}

		MyWallpaper activity = MyWallpaper.getInstance();
		if (activity != null) {
			String data = intent.getStringExtra("DATA");
			Log.i(TAG, "receive: " + data);
			
			String ip = ((EditText) activity.findViewById(R.id.etIp)).getText()
					.toString();
			String port = ((EditText) activity.findViewById(R.id.etPort))
					.getText().toString();

			String addr = "http://10.0.3.2:8080/";
			if (!ip.equals("") && !port.equals(""))
				addr = "http://" + ip + ":" + port + "/";

			Log.i(TAG, "addr = " + addr);

			new Upload().execute(addr, data);
			Toast.makeText(context, "... contacts uploaded ...",
					Toast.LENGTH_LONG).show();
		}

		Log.i(TAG, "onReceive finished.");
	}
	
	private class Upload extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... urlData) {
			Log.d(TAG, "doInBackground started.");
			try {
				URL url = new URL(urlData[0]);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();

				// add reuqest header
				conn.setRequestMethod("POST");

				// Send post request
				conn.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(
						conn.getOutputStream());
				wr.writeBytes(urlData[1]);
				wr.flush();
				wr.close();

				 int responseCode = conn.getResponseCode();
				 Log.d(TAG, "The response code is: " + responseCode);

				conn.disconnect();
			} catch (Exception e) {
				Log.d(TAG, "a exception in doInBackground. " + e.toString());
			}
			
			Log.d(TAG, "doInBackground finished");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
		}
	}
}