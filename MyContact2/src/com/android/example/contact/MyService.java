package com.android.example.contact;

import com.android.example.contact.task.GetContactsTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	public static final String TAG = "MyService : ";
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand started.");
		
		// Let it continue running until it is stopped.
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		
		if (GetContactsTask.pairString == null)
			new GetContactsTask(null, this.getApplicationContext()).execute(false);
		
		Log.i(TAG, "onStartCommand finished.");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy started.");
		
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		
		Log.i(TAG, "onDestroy finished.");
	}
}