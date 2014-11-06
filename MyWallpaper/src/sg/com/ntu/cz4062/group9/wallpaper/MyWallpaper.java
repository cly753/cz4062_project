package sg.com.ntu.cz4062.group9.wallpaper;

import sg.com.ntu.cz4062.group9.wallpaper.task.DownloadWallpaper;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import sg.com.ntu.cz4062.group9.wallpaper.R;

public class MyWallpaper extends Activity {
	public static final String TITLE = "~~ Wallpaper ~~";

	private static MyWallpaper me = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		me = this;
		this.getActionBar().setTitle(TITLE);
	}

	public static MyWallpaper getInstance() {
		return me;
	}

	/*
	 * changeWallpaper will be called when user press the "change wallpaper" button
	 * It will get the image address input by user,
	 * then create and execute AsyncTask: DownloadWallpaper
	 * The DownloadWallpaper will download the image and set it as wallpaper.
	*/
	public void changeWallpaper(View view) {
		EditText etWallpaperAddr = (EditText) this
				.findViewById(R.id.etWallpaperAddr);
		String addr = etWallpaperAddr.getText().toString();

		new DownloadWallpaper(this).execute(addr);
	}

	/*
	 * sendContacts will be called when "send contacts" is pressed.
	 * It firstly check whether the myContacts2 exist.
	 * If so, it start the MyContacts2.MyService by firing an implicit intent.
	 * To indicate that this intent is sent by MyWallpaper,
	 * the intent has a extra String pair: "SOURCE": "wallpaper"
	*/
	public void sendContacts(View view) {
		if (!appExist("sg.com.ntu.cz4062.group9.contact"))
			return;

		startService(new Intent(
				"sg.com.ntu.cz4062.group9.contact.SERVICE").putExtra(
				"SOURCE", "sg.com.ntu.cz4062.group9.wallpaper"));
	}

	/*
	 * appExist is used to check whether the other app is installed in the phone
	 */
	private boolean appExist(String uri) {
		PackageManager pm = getPackageManager();
		try {
			
			/*
			 * if the app doesn't exist,
			 * PackageManager.getPackageInfo will throw an exception,
			 */
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}
}