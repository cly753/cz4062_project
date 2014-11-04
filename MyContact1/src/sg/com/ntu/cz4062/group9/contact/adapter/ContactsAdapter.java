package sg.com.ntu.cz4062.group9.contact.adapter;

import java.util.List;

import sg.com.ntu.cz4062.group9.contact.data.Pair;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import sg.com.ntu.cz4062.group9.contact.R;

public class ContactsAdapter extends ArrayAdapter<Pair> {
	private static String TAG = "ContactsAdapter : ";
	
	private List<Pair> pairList = null;
	private final Context context;

	public ContactsAdapter(Context context, List<Pair> objects) {
		super(context, R.layout.list_item, objects);
		this.context = context;
		this.pairList = objects;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
//		Log.d(TAG, "getView started");
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView keyView = (TextView) rowView.findViewById(R.id.key);
		TextView valueView = (TextView) rowView.findViewById(R.id.value);
		
		Pair pair = (Pair) getItem(position);
		keyView.setText(pair.key);
		valueView.setText(pair.value);
		
//		Log.i(TAG, "getView finished");
		return rowView;
	}
}