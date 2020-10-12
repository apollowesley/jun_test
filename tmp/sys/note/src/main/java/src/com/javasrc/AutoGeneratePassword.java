package com.javasrc;

public class AutoGeneratePassword {

	private final static String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z" };

	public static void main(String[] args) {
		GeneratePassword();
	}

	public static String GeneratePassword() {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < 7; i++) {
			Integer thenum = (int) (Math.random() * 36);
			resultSb.append(strs[thenum]);
		}
		return resultSb.toString();
	}
}
