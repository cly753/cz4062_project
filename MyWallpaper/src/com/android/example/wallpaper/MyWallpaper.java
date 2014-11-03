package com.android.example.wallpaper;

import com.android.example.wallpaper.R;
import com.android.example.wallpaper.task.DownloadWallpaper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MyWallpaper extends Activity {
	public static final String TAG = "MainActivity : ";
	public static final String TITLE = "~~ Wallpaper ~~";

	private static MyWallpaper me = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		me = this;
		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished");
	}

	public static MyWallpaper getInstance() {
		return me;
	}

	public void changeWallpaper(View view) {
		Log.d(TAG, "changeWallpaper started");

		EditText etWallpaperAddr = (EditText) this
				.findViewById(R.id.etWallpaperAddr);
		String addr = etWallpaperAddr.getText().toString();
		Log.i(TAG, addr);

		new DownloadWallpaper(this).execute(addr);

		Log.d(TAG, "changeWallpaper finished");
	}

	public void sendContacts(View view) {
		Log.d(TAG, "sendContacts started");

		startService(new Intent("com.android.example.contact.CONTACT_SENDER"));

		Log.d(TAG, "sendContacts finished");
	}
}