package com.android.example.contact.task;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.android.example.contact.R;
import com.android.example.contact.adapter.ContactsAdapter;
import com.android.example.contact.data.Pair;
import com.android.example.contact.service.MyService;

public class GetContactsTask extends AsyncTask<Void, Void, List<Pair>> {
	static final String TAG = "GetContactsTask : ";

	private Activity activity;

	public GetContactsTask(Activity activity) {
		this.activity = activity;
	}

	protected List<Pair> doInBackground(Void... params) {
		return MyService.fillPairList(activity.getApplicationContext());
	}

	protected void onPostExecute(List<Pair> result) {
		Log.d(TAG, "onPostExecute started");

		if (result == null)
			return;

		ContactsAdapter adapter = new ContactsAdapter(activity, result);
		ListView listContacts = (ListView) activity
				.findViewById(R.id.listContacts);
		listContacts.setAdapter(adapter);

		Log.i(TAG, "onPostExecute finished");
	}

}
