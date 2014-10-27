package com.android.example.contact;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.example.contact.task.ListProfileTask;

public class MyContacts extends Activity {
	static final String TAG = "MainActivity : ";
	ActionBar actionBar;
	TabListener tabListener;
	Tab profileTab;
	
	public TextView textViewForDebug;
	
	public static final String TAB_TEXT = "~~ Contacts ~~";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		tabListener = new TabListener(this);

		profileTab = actionBar.newTab().setText(TAB_TEXT)
				.setTabListener(tabListener);
		actionBar.addTab(profileTab);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_USE_LOGO);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Log.i(TAG, "" + Thread.currentThread().getId());
		
	}

	class TabListener implements ActionBar.TabListener {
		String lastTab = null;
		private Activity activity;

		public TabListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) { // select by default ?
			Log.i(TAG, "" + Thread.currentThread().getId());
			CharSequence tabText = tab.getText();
			Log.i(TAG, tabText.toString());
			if (tabText.equals(TAB_TEXT)) {
				ListProfileTask task = new ListProfileTask(activity, ft);
				task.execute();
				Log.i(TAG, "task executing");
			}
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
	}
}