package sg.com.ntu.cz4062.group9.contact.adapter;

import java.util.List;

import sg.com.ntu.cz4062.group9.contact.data.Pair;

public class ListToStringAdapter {
	public static String listToString(List<Pair> contactsList) {
		String ans = "";
		for (Pair temp : contactsList)
			ans += temp.key + "=" + temp.value + "&";
		return ans.substring(0, ans.length() - 1);
	}
}
