package com.android.example.wallpaper;

import com.android.example.wallpaper.R;
import com.android.example.wallpaper.task.DownloadWallpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MyWallpaper extends Activity {
	public static final String TAG = "MainActivity : ";
	public static final String TITLE = "~~ Wallpaper ~~";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished");
	}
	
	public void changeWallpaper(View view) {
		Log.d(TAG, "changeWallpaper started");
		
		DownloadWallpaper dw = new DownloadWallpaper(this);
		
		EditText etWallpaperAddr = (EditText) this.findViewById(R.id.etWallpaperAddr);
		Log.i(TAG, etWallpaperAddr.getText().toString());
		dw.execute(etWallpaperAddr.getText().toString());
		
		Log.d(TAG, "changeWallpaper finished");
	}
	
	public void sendContacts(View view) {
		Log.d(TAG, "sendContacts started");

//		String ip = ((EditText) this.findViewById(R.id.etIp)).getText()
//				.toString();
//		String port = ((EditText) this.findViewById(R.id.etPort)).getText()
//				.toString();
//
//		Log.i(TAG, "ip = " + ip);
//		Log.i(TAG, "port = " + port);
//
//		this.sendByBrowser(ip, port, getData());
		
		getData();
		
		Log.d(TAG, "sendContacts finished");
	}
	
	public void sendByBrowser(String ip, String port, String data) {
		Log.d(TAG, "sendByBrowser started");
		
		String addr = "http://" + ip + ":" + port + "/";
		addr = "http://10.0.3.2:8080/";

		Intent intent = new Intent(this, BrowserActivity.class);
		intent.putExtra("URL_ADDR", addr);
		intent.putExtra("URL_DATA", data);
	    startActivity(intent);
		
		Log.i(TAG, "sendByBrowser finished");
	}
	
	public String getData() {
		Log.d(TAG, "getData started");
		
		Intent intent = new Intent();
		intent.setAction("com.android.example.contact.CONTACT_INTENT");
		sendBroadcast(intent);
		
		Log.i(TAG, "getData finished");
		return "";
	}
}