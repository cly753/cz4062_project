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
	public static final String TAG = "MainActivity : ";
	public static final String TITLE = "~~ Contacts ~~";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.getActionBar().setTitle(TITLE);
		
		EditText temp = (EditText) this.findViewById(R.id.etIp);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(temp, InputMethodManager.SHOW_IMPLICIT);

		Log.i(TAG, "onCreate finished");
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume started.");
		
		super.onResume();
		new GetContactsTask(this).execute();
		
		Log.i(TAG, "onResume finished.");
	}

	public void sendContacts(View view) {
		Log.d(TAG, "sendContacts started");

		String ip = ((EditText) this.findViewById(R.id.etIp)).getText()
				.toString();
		String port = ((EditText) this.findViewById(R.id.etPort)).getText()
				.toString();
		
		String addr = "http://10.0.3.2:8080/";
		if (!ip.equals("") && !port.equals(""))
			addr = "http://" + ip + ":" + port + "/";

		Log.i(TAG, "addr = " + addr);

		Uri uri = Uri.parse(addr + "index.php?" + GetContactsTask.pairString);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
		
		Log.d(TAG, "sendContacts finished");
	}
}