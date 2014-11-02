package com.android.example.contact;

import org.apache.http.util.EncodingUtils;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.example.contact.task.ListProfileTask;

public class MyContacts extends Activity {
	static final String TAG = "MainActivity : ";
	
	// TabListener tabListener;
	Tab profileTab;

	ListView listContacts;
	EditText etIp;
	EditText etPort;
	Button btnSend;

	public static final String TITLE = "~~ Contacts ~~";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished");
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume started.");
		
		super.onResume();
		ListProfileTask task = new ListProfileTask(this);
		task.execute();
		
		Log.i(TAG, "onResume finished.");
	}

	public void sendContacts(View view) {
		Log.d(TAG, "sendContacts started");

		String ip = ((EditText) this.findViewById(R.id.etIp)).getText()
				.toString();
		String port = ((EditText) this.findViewById(R.id.etPort)).getText()
				.toString();

		Log.i(TAG, "ip = " + ip);
		Log.i(TAG, "port = " + port);

		this.sendByBrowser(ip, port, ListProfileTask.pairString);
		
		Log.d(TAG, "sendContacts finished");
	}
	
	public void sendByBrowser(String ip, String port, String data) {
		Log.d(TAG, "sendByBrowser started");
		
		String addr = "http://" + ip + ":" + port + "/";
		addr = "http://10.0.3.2:8080/";

		Intent intent = new Intent(this, BrowserActivity.class);
		intent.putExtra("URL_ADDR", addr);
		intent.putExtra("URL_DATA", data);
	    startActivity(intent);
		
		Log.i(TAG, "sendByBrowser finished");
	}
}