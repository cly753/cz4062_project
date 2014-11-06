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

public class MyService extends Service {

	/*
	 * This service is used to retrieve the contacts,
	 * parse into HTTP GET format,
	 * and use implicit intent to send the contacts data back to myWallpaper.
	 * It will only start when an intent is fired with action string "... .SERVICE"
	 * which is specified in AndroidManifest.xml.	
	*/
	
	/*
	 * onStartCommand will be called when the service is started.
	*/
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		/*
		 * It should check that the intent is sent from myWallpaper,
		 * or if the service is started from another activity of myContact2,
		 * it should check that the myWallpaper is installed in the phone.
		*/
		String source = intent.getStringExtra("SOURCE");
		if (source != null
				&& source.equals("sg.com.ntu.cz4062.group9.wallpaper")
				&& appExist("sg.com.ntu.cz4062.group9.wallpaper")) {

			/*
			 * call the method: fillPairList to retrieve the contacts list
			*/
			String data = ListToStringAdapter.listToString(getContacts(this
					.getApplicationContext()));

			/*
			 * Once the contacts list is got, an intent will be fired
			 * with two String pair extra:
			 * one is DATA pair, for the contacts string
			 * the other is SOURCE pair,
			 * to indicate that the intent is sent from myContact2
			*/
			sendBroadcast(new Intent(
					"sg.com.ntu.cz4062.group9.wallpaper.RECEIVER")
					.putExtra("DATA", data).putExtra("SOURCE",
							"sg.com.ntu.cz4062.group9.contact"));
			
			Log.d("service : ", "called.");
		}

		stopSelf();
		return Service.START_STICKY;
	}

	/*
	 * This service doesn't allow other process to bind, so return null
	*/
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	/*
	 * FillPairList will send query to the content provider of
	 * contacts, get the contacts and store in List<Pair>
	 */
	public static List<Pair> getContacts(Context context) {
		
		/*
		 * send query to the content provider of system contacts
		 * with projection (only query name and whether has phone number)
		 */
		Cursor c = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI,
				new String[] { ContactsContract.Contacts._ID,
						ContactsContract.Contacts.DISPLAY_NAME,
						ContactsContract.Contacts.HAS_PHONE_NUMBER }, null,
				null, null);

		/*
		 * the List<Pair> is used to hold the contacts data
		 */
		List<Pair> contactsList = new LinkedList<Pair>();
		while (c != null && c.moveToNext()) {
			Pair pair = new Pair("-", "-");
			pair.key = c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			/*
			 * for each contanct record, if one contact doesn't contains phone number,
			 * it will be ignored
			 */
			if (Integer
					.parseInt(c.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) == 0)
				continue;

			String id = c.getString(c
					.getColumnIndex(ContactsContract.Contacts._ID));

			/*
			 * since the phone number is stored in a different database table,
			 * another query is needed to get the phone number.
			 * only query the first phone number if there are more than one
			 */
			Cursor pCur = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					new String[] {
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
							ContactsContract.CommonDataKinds.Phone.DATA1 },
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
					new String[] { id }, null);

			if (pCur != null && pCur.moveToNext()) {
				
				/*
				 * the retrieved phone number is stored in the pair, and added into the List<Pair>
				 */
				pair.value = pCur
						.getString(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
				contactsList.add(pair);
				pCur.close();
			}
		}
		if (c != null)
			c.close();

		return contactsList;
	}

	/*
	 * appExist is used to check whether the other app is installed in the phone
	 */
	private boolean appExist(String uri) {
		PackageManager pm = getPackageManager();
		try {
			
			/*
			 * if the app doesn't exist,
			 * PackageManager.getPackageInfo will throw an exception,
			 */
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}
}