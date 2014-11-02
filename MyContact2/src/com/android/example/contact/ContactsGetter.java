//package com.android.example.contact;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.net.Uri;
//import android.provider.ContactsContract;
//import android.util.Log;
//
//public class ContactsGetter extends ContentProvider {
//	static final String PROVIDER_NAME = "com.android.example.ContactsGetter";
//	static final String URL = "content://" + PROVIDER_NAME + "/contacts";
//	static final Uri CONTENT_URI = Uri.parse(URL);
//	
//	private static final String TAG = "ContactsGetter : ";
//	
//	private static Context context = null;
//	
//	public ContactsGetter(Context context) {
//		this.context = context;
//	}
//
//	@Override
//	public boolean onCreate() {
//		Log.i(TAG, "onCreate started.");
//		
//		return true;
//	}
//
//	@Override
//	public int delete(Uri arg0, String arg1, String[] arg2) {
//		return 0;
//	}
//
//	@Override
//	public Uri insert(Uri arg0, ContentValues arg1) {
//		return null;
//	}
//
//	@Override
//	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
//			String arg4) {
//		
//		String toshow = arg0.toString() + " ";
//		for (int i = 0; i < arg1.length; i++)
//			toshow += arg1[i] + " ";
//		toshow += arg2 + " " + arg3;
//		Log.i(TAG, "query " + toshow );
//		
//		
//		Cursor c = context.getApplicationContext().getContentResolver().query(
//				ContactsContract.Contacts.CONTENT_URI,
//				new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
//						ContactsContract.Contacts.HAS_PHONE_NUMBER }, null,
//				null, null);
//		
//		Log.i(TAG, "here " + c.toString());
//		return c;
//	}
//
//	@Override
//	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public String getType(Uri uri) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
