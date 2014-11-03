package com.android.example.contact.service;

import java.util.LinkedList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.android.example.contact.adapter.ListToStringAdapter;
import com.android.example.contact.data.Pair;

public class MyService extends Service {
	public static final String TAG = "MyService : ";

	@Override
	public void onCreate() {
		Log.d(TAG, "Service onCreate started.");

		Log.i(TAG, "Service onCreate started.");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Service onStartCommand started.");

		Toast.makeText(getApplicationContext(), "~ MyContacts MyService ~",
				Toast.LENGTH_SHORT).show();

		String ans = ListToStringAdapter.listToString(fillPairList(this
				.getApplicationContext()));
		Log.i(TAG, "retrieved: " + ans);

		sendBroadcast(new Intent(
				"com.android.example.wallpaper.CONTACT_RECEIVER").putExtra(
				"ANS", ans));

		stopSelf();
		Log.i(TAG, "Service onStartCommand finished.");
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "Service onDestroy started.");

		super.onDestroy();

		Log.i(TAG, "Service onDestroy finished.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static List<Pair> fillPairList(Context context) {
		Log.d(TAG, "retrieve contacts started");

		Cursor c = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI,
				new String[] { ContactsContract.Contacts._ID,
						ContactsContract.Contacts.DISPLAY_NAME,
						ContactsContract.Contacts.HAS_PHONE_NUMBER }, null,
				null, null);

		Log.i(TAG, "fill name...found = " + c.getCount());

		List<Pair> contactsList = new LinkedList<Pair>();
		while (c != null && c.moveToNext()) {
			Pair pair = new Pair("xxx", "xxx");

			pair.key = c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			if (Integer
					.parseInt(c.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) >= 0) {
				String id = c.getString(c
						.getColumnIndex(ContactsContract.Contacts._ID));

				Cursor pCur = context.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = ?", new String[] { id }, null);

				boolean firstNumber = true;
				while (pCur != null && pCur.moveToNext()) {
					for (String oneColumn : pCur.getColumnNames()) {
						String oneString = pCur.getString(pCur
								.getColumnIndex(oneColumn));

						if (oneColumn.compareTo("data1") == 0
								&& oneString != null) {
							if (!firstNumber) {
								Pair tempPair = new Pair("", oneString);
								contactsList.add(tempPair);
							} else {
								pair.value = oneString;
								contactsList.add(pair);
								firstNumber = false;
							}
						}
					}
				}
				if (pCur != null)
					pCur.close();
			}
		}
		if (c != null)
			c.close();

		Log.i(TAG, ListToStringAdapter.listToString(contactsList));
		Log.i(TAG, "retrieve contacts finished");
		return contactsList;
	}
}