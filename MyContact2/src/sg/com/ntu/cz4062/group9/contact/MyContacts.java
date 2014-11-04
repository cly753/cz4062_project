package sg.com.ntu.cz4062.group9.contact;

import sg.com.ntu.cz4062.group9.contact.R;
import sg.com.ntu.cz4062.group9.contact.task.GetContactsTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyContacts extends Activity {
	static final String TAG = "MyContacts : ";
	private static final String TITLE = "~~ Contacts ~~";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate started.");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished.");
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume started.");

		super.onResume();
		new GetContactsTask(this).execute();
		
		startService(new Intent(
				"sg.com.ntu.cz4062.group9.contact.CONTACT_SENDER"));
		
		Log.i(TAG, "onResume finished.");
	}
}