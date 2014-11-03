package com.android.example.contact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	public static final String TAG = "MyReceiver : ";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive started.");
		
		Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
		
		Log.i(TAG, "onReceive finished.");
	}
}