package sg.com.ntu.cz4062.group9.contact.data;

public class Pair {
	public String key;
	public String value;

	/*
	 * a utility class for storing each contacts data
	 * key: contact name
	 * value: contact number
	*/
	public Pair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String toString() {
		return "{" + key + ", " + value + "}";
	}
}
