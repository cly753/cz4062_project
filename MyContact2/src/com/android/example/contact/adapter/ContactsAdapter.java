package com.android.example.contact.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.example.contact.R;
import com.android.example.contact.data.Pair;

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
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView keyView = (TextView) rowView.findViewById(R.id.key);
		TextView valueView = (TextView) rowView.findViewById(R.id.value);
		
		Pair pair = (Pair) getItem(position);
		keyView.setText(pair.key);
		valueView.setText(pair.value);

		return rowView;
	}
}