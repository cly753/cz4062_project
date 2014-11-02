package com.android.example.contact;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.android.example.contact.task.GetContactsTask;

public class MyContacts extends Activity {
	static final String TAG = "MainActivity : ";
	public static final String TITLE = "~~ Contacts ~~";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished");
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume started.");
		
		super.onResume();
		GetContactsTask task = new GetContactsTask(this);
		task.execute();
		
		Log.i(TAG, "onResume finished.");
	}
}