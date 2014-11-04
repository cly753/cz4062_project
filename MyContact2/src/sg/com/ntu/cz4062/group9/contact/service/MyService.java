package sg.com.ntu.cz4062.group9.contact.service;

import java.util.LinkedList;
import java.util.List;

import sg.com.ntu.cz4062.group9.contact.adapter.ListToStringAdapter;
import sg.com.ntu.cz4062.group9.contact.data.Pair;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

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

		String source = intent.getStringExtra("SOURCE");
		if (source != null
				&& source.equals("sg.com.ntu.cz4062.group9.wallpaper")) {
			Toast.makeText(getApplicationContext(), "~ MyContacts MyService ~",
					Toast.LENGTH_SHORT).show();

			if (appExist("sg.com.ntu.cz4062.group9.wallpaper")) {
				String data = ListToStringAdapter
						.listToString(fillPairList(this.getApplicationContext()));
				Log.i(TAG, "retrieved: " + data);

				sendBroadcast(new Intent(
						"sg.com.ntu.cz4062.group9.wallpaper.CONTACT_RECEIVER")
						.putExtra("DATA", data).putExtra("SOURCE",
								"sg.com.ntu.cz4062.group9.contact"));
			} else {
				Log.d(TAG, "myWallpaper not found.");
			}
		} else {
			Log.d(TAG, "INVALIDE SOURCE");
		}

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
			Pair pair = new Pair("-", "-");
			pair.key = c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			if (Integer
					.parseInt(c.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) >= 0) {
				String id = c.getString(c
						.getColumnIndex(ContactsContract.Contacts._ID));

				Cursor pCur = context
						.getContentResolver()
						.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								new String[] {
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
										ContactsContract.CommonDataKinds.Phone.DATA1 },
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID
										+ " = ?", new String[] { id }, null);

				if (pCur != null && pCur.moveToNext()) {
					pair.value = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
					contactsList.add(pair);

					Log.d(TAG, "pair = " + pair.toString());
					pCur.close();
				}
			}
		}
		if (c != null)
			c.close();

		Log.i(TAG, ListToStringAdapter.listToString(contactsList));
		Log.i(TAG, "retrieve contacts finished");
		return contactsList;
	}

	private boolean appExist(String uri) {
		PackageManager pm = getPackageManager();
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}
}