package sg.com.ntu.cz4062.group9.contact;

import sg.com.ntu.cz4062.group9.contact.R;
import sg.com.ntu.cz4062.group9.contact.task.GetContactsTask;
import android.app.Activity;
import android.os.Bundle;

public class MyContacts extends Activity {
	private static final String TITLE = "~~ Contacts ~~";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.getActionBar().setTitle(TITLE);
	}
	
	/*
	 * Each time the Activity is resumed, 
	 * the GetContactsTask will be executed once
	 * to retrieve the contacts and upate UI
	 */
	@Override
	protected void onResume() {
		super.onResume();
		new GetContactsTask(this).execute();
	}
}