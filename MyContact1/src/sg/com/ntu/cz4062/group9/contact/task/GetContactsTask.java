package sg.com.ntu.cz4062.group9.contact.task;

import java.util.LinkedList;
import java.util.List;

import sg.com.ntu.cz4062.group9.contact.adapter.*;
import sg.com.ntu.cz4062.group9.contact.data.Pair;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.ListView;
import sg.com.ntu.cz4062.group9.contact.R;

public class GetContactsTask extends AsyncTask<Void, Void, List<Pair>> {
	public static String pairString;
	private Activity activity;

	/*
	 * this is an AsyncTask class for querying contacts from contacts.content
	 * provider and update UI
	 */
	public GetContactsTask(Activity act) {
		this.activity = act;
	}

	/*
	 * When the AsyncTask:GetContactsTask is executed, the doInBackground will
	 * be called. It will send query to the content provider of
	 * contacts, get the contacts, store it as a static String,
	 * and then pass the contacts data to onPostExecute.
	 */
	@Override
	protected List<Pair> doInBackground(Void... params) {
		
		/*
		 * send query to the content provider of system contacts
		 * with projection (only query name and whether has phone number)
		*/
		Cursor c = activity.getContentResolver().query(
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
			Cursor pCur = activity.getContentResolver().query(
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

		/*
		 * Finally, store the contacts list in the form of String for future upload
		 * and return the contacts list
		*/
		pairString = ListToStringAdapter.listToString(contactsList);
		return contactsList;
	}

	/*
	 * When doInBackground finished, onPostExecute will be called it will use
	 * the retrieved contacts, stored in the List<Pair>, to update UI : ListView
	 */
	@Override
	protected void onPostExecute(List<Pair> result) {
		ContactsAdapter adapter = new ContactsAdapter(activity, result);
		((ListView) activity.findViewById(R.id.listContacts))
				.setAdapter(adapter);
	}
}
