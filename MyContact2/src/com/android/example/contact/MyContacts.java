package com.android.example.contact;

import com.android.example.contact.service.MyService;
import com.android.example.contact.task.GetContactsTask;

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

public class MyContacts extends Activity {
	static final String TAG = "MyContacts : ";
	public static final String TITLE = "~~ Contacts ~~";

	Messenger mService = null;
	boolean onBound = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate started.");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished.");
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume started.");

		super.onResume();
		new GetContactsTask(this).execute(); // true: update UI

		bindService(new Intent("com.android.example.contact.CONTACT_INTENT"),
				mConnection, Context.BIND_AUTO_CREATE);

		Log.i(TAG, "onResume finished.");
	}

	@Override
	protected void onStop() {
		super.onStop();

		disconnect();
	}

	private void disconnect() {
		if (onBound) {
			unbindService(mConnection);
			mService = null;
			onBound = false;
		}
	}

	public void sayHello() {
		if (!onBound)
			return ;

		Message msg = Message.obtain(null, MyService.MSG_SAY_HELLO, 0, 0);
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			onBound = true;

			sayHello();
			disconnect();
		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
			onBound = false;
		}
	};
}