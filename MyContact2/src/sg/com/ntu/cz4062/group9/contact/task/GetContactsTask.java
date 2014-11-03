package sg.com.ntu.cz4062.group9.contact.task;

import java.util.List;

import sg.com.ntu.cz4062.group9.contact.adapter.ContactsAdapter;
import sg.com.ntu.cz4062.group9.contact.data.Pair;
import sg.com.ntu.cz4062.group9.contact.service.MyService;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import sg.com.ntu.cz4062.group9.contact.R;

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
