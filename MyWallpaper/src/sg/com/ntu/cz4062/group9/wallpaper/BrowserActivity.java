package sg.com.ntu.cz4062.group9.wallpaper;

import org.apache.http.util.EncodingUtils;

import sg.com.ntu.cz4062.group9.wallpaper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class BrowserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		
		Intent intent = this.getIntent();
		String addr = intent.getStringExtra("URL_ADDR");
		String data = intent.getStringExtra("URL_DATA");
		
		WebView webview = new WebView(this);
		byte[] post = EncodingUtils.getBytes(data, "BASE64");
		webview.postUrl(addr + "index.php", post);
		
		setContentView(webview);
	}
}
