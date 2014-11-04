package sg.com.ntu.cz4062.group9.contact.task;

import java.util.LinkedList;
import java.util.List;

import sg.com.ntu.cz4062.group9.contact.adapter.*;
import sg.com.ntu.cz4062.group9.contact.data.Pair;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import sg.com.ntu.cz4062.group9.contact.R;

public class GetContactsTask extends AsyncTask<Void, Void, List<Pair>> {
	static final String TAG = "ListProfileTask : ";

	public static String pairString;
	private Activity activity;

	public GetContactsTask(Activity act) {
		this.activity = act;
	}

	protected List<Pair> doInBackground(Void... params) {
		return fillPairList();
	}

	protected void onPostExecute(List<Pair> result) {
		Log.d(TAG, "onPostExecute started");

		ContactsAdapter adapter = new ContactsAdapter(activity, result);
		((ListView) activity.findViewById(R.id.listContacts))
				.setAdapter(adapter);

		Log.i(TAG, "onPostExecute finished");
	}

	public List<Pair> fillPairList() {
		Log.d(TAG, "retrieve contacts started");

		Cursor c = activity.getContentResolver().query(
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

				Cursor pCur = activity
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

		pairString = ListToStringAdapter.listToString(contactsList);
		Log.i(TAG, pairString);
		Log.i(TAG, "retrieve contacts finished");
		return contactsList;
	}
}
