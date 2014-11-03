package sg.com.ntu.cz4062.group9.contact.adapter;

import java.util.List;

import sg.com.ntu.cz4062.group9.contact.data.Pair;

public class ListToStringAdapter {
	public static String listToString(List<Pair> contactsList) {
		String ans = "";
		String lastKey = "";
		int count = 0;
		for (Pair temp : contactsList) {
			if (!temp.key.equals("")) {
				lastKey = temp.key;
				count = 0;
			}
			count++;
			
			ans += lastKey + "_" + count + "=" + temp.value + "&";
		}
		return ans.substring(0, ans.length() - 1);
	}
}
