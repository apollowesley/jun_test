package cn.coder.jdbc.core;

import java.lang.reflect.Field;

public final class BeanMapping {
	private String[] keys = new String[0];
	private Field[] values = new Field[0];

	public Field get(String label) {
		int index = findIndex(keys, label);
		if (index < 0)
			return null;
		return values[index];
	}

	public void put(String label, Field field) {
		int index = findIndex(keys, label);
		if (index < 0) {
			String[] temp1 = new String[keys.length + 1];
			System.arraycopy(keys, 0, temp1, 0, keys.length);
			temp1[keys.length] = label;
			keys = temp1;

			Field[] temp2 = new Field[values.length + 1];
			System.arraycopy(values, 0, temp2, 0, values.length);
			temp2[values.length] = field;
			values = temp2;
		}
	}

	public String[] keys() {
		return this.keys;
	}

	private static int findIndex(String[] arr, String key) {
		int len = arr.length;
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				if (arr[i].compareTo(key) == 0) {
					return i; // key found.
				}
			}
		}
		return -1;// key not found.
	}

}
