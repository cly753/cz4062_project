package com.android.example.contact;

import com.android.example.contact.task.GetContactsTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyContacts extends Activity {
	static final String TAG = "MyContacts : ";
	public static final String TITLE = "~~ Contacts ~~";

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
		new GetContactsTask(this).execute();

		Log.i(TAG, "onResume finished.");
	}
}