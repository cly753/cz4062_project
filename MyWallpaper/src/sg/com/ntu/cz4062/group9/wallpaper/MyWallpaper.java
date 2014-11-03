package sg.com.ntu.cz4062.group9.wallpaper;

import sg.com.ntu.cz4062.group9.wallpaper.task.DownloadWallpaper;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import sg.com.ntu.cz4062.group9.wallpaper.R;

public class MyWallpaper extends Activity {
	public static final String TAG = "MainActivity : ";
	public static final String TITLE = "~~ Wallpaper ~~";

	private static MyWallpaper me = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		me = this;
		this.getActionBar().setTitle(TITLE);

		Log.i(TAG, "onCreate finished");
	}

	public static MyWallpaper getInstance() {
		return me;
	}

	public void changeWallpaper(View view) {
		Log.d(TAG, "changeWallpaper started");

		EditText etWallpaperAddr = (EditText) this
				.findViewById(R.id.etWallpaperAddr);
		String addr = etWallpaperAddr.getText().toString();
		Log.i(TAG, addr);

		new DownloadWallpaper(this).execute(addr);

		Log.d(TAG, "changeWallpaper finished");
	}

	public void sendContacts(View view) {
		Log.d(TAG, "sendContacts started");

		if (appExist("sg.com.ntu.cz4062.group9.contact"))
			startService(new Intent("sg.com.ntu.cz4062.group9.contact.CONTACT_SENDER"));
		else
			Log.d(TAG, "myContacts not found.");

		Log.d(TAG, "sendContacts finished");
	}
	
    private boolean appExist(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}