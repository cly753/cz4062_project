package com.android.example.contact.service;

import java.util.LinkedList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.android.example.contact.adapter.ListToStringAdapter;
import com.android.example.contact.data.Pair;

public class MyService extends Service {
	public static final String TAG = "MyService : ";

	public static final int MSG_SAY_HELLO = 1;
	public static final int RETURN_CONTACTS = 2;

	final Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind started.");

		Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT)
				.show();

		Log.i(TAG, "onBind finished.");
		return mMessenger.getBinder();
	}

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SAY_HELLO:
				Toast.makeText(getApplicationContext(), "hello!",
						Toast.LENGTH_SHORT).show();
				break;
			case RETURN_CONTACTS:
				Toast.makeText(getApplicationContext(), "RETURN_CONTACTS",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}
	
    public String getNameNumberString() {
        return ListToStringAdapter.listToString(fillPairList(this.getApplicationContext()));
    }
    
//	public static List<Pair> fillPairList(Activity activity) {
	public static List<Pair> fillPairList(Context context) {
		Log.d(TAG, "retrieve contacts started");

//		Cursor c = activity.getContentResolver().query(
//				ContactsContract.Contacts.CONTENT_URI,
//				new String[] { ContactsContract.Contacts._ID,
//						ContactsContract.Contacts.DISPLAY_NAME,
//						ContactsContract.Contacts.HAS_PHONE_NUMBER }, null,
//				null, null);

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

//				Cursor pCur = activity.getContentResolver().query(
//						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//						null,
//						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//								+ " = ?", new String[] { id }, null);
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