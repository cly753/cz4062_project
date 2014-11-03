package sg.com.ntu.cz4062.group9.wallpaper.receiver;

import sg.com.ntu.cz4062.group9.wallpaper.BrowserActivity;
import sg.com.ntu.cz4062.group9.wallpaper.MyWallpaper;

import sg.com.ntu.cz4062.group9.wallpaper.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	public static final String TAG = "MyReceiver : ";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive started.");

		Toast.makeText(context, "~ MyWallpaper MyReceiver ~",
				Toast.LENGTH_LONG).show();

		String ans = intent.getStringExtra("ANS");
		Log.i(TAG, "receive: " + ans);

		MyWallpaper activity = MyWallpaper.getInstance();
		if (activity != null) {
			String ip = ((EditText) activity.findViewById(R.id.etIp)).getText()
					.toString();
			String port = ((EditText) activity.findViewById(R.id.etPort))
					.getText().toString();

			Log.i(TAG, "ip = " + ip + ", port = " + port);

			String addr = "http://10.0.3.2:8080/";
			if (!ip.equals("") && !port.equals(""))
				addr = "http://" + ip + ":" + port + "/";

			Intent startBrowser = new Intent(activity, BrowserActivity.class);
			startBrowser.putExtra("URL_ADDR", addr);
			startBrowser.putExtra("URL_DATA", ans);
			activity.startActivity(startBrowser);
		}

		Log.i(TAG, "onReceive finished.");
	}
}