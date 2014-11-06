package sg.com.ntu.cz4062.group9.contact.task;

import java.util.List;

import sg.com.ntu.cz4062.group9.contact.adapter.ContactsAdapter;
import sg.com.ntu.cz4062.group9.contact.data.Pair;
import sg.com.ntu.cz4062.group9.contact.service.MyService;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import sg.com.ntu.cz4062.group9.contact.R;

public class GetContactsTask extends AsyncTask<Void, Void, List<Pair>> {
	private Activity activity;

	/*
	 * this is an AsyncTask class for querying contacts from contacts.content
	 * provider and update UI
	 */
	public GetContactsTask(Activity activity) {
		this.activity = activity;
	}

	/*
	 * When the AsyncTask:GetContactsTask is executed, the doInBackground will
	 * be called. It will use MyService.fillPairList to get the contacts, and
	 * then pass the contacts data to onPostExecute.
	 */
	protected List<Pair> doInBackground(Void... params) {
		return MyService.getContacts(activity.getApplicationContext());
	}

	/*
	 * When doInBackground finished, onPostExecute will be called it will use
	 * the retrieved contacts, stored in the List<Pair>, to update UI : ListView
	 */
	protected void onPostExecute(List<Pair> result) {
		if (result == null)
			return;

		ContactsAdapter adapter = new ContactsAdapter(activity, result);
		((ListView) activity.findViewById(R.id.listContacts))
				.setAdapter(adapter);
	}
}
