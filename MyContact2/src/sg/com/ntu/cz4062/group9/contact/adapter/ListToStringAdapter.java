package sg.com.ntu.cz4062.group9.contact.adapter;

import java.util.List;

import sg.com.ntu.cz4062.group9.contact.data.Pair;

public class ListToStringAdapter {
	
	/*
	 * this is a utility class
	 * accept a List<Pair> object containing contacts data
	 * return the string in the format of HTTP GET
	 */ 
	public static String listToString(List<Pair> contactsList) {
		String ans = "";
		for (Pair temp : contactsList)
			ans += temp.key + "=" + temp.value + "&";
		return ans.substring(0, ans.length() - 1);
	}
}