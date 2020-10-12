package cn.coder.jdbc;

import java.util.Arrays;

public class TestArrays {
	public static void main(String[] args) {
		String[] arr = new String[] { "cc", "bb", "aa" };
		Arrays.binarySearch(arr, "cc");
		long start = System.nanoTime();
		System.out.println(find(arr, "cc"));
		long n = System.nanoTime();
		System.out.println(n - start);
		System.out.println(Arrays.binarySearch(arr, "cc"));
		System.out.println(System.nanoTime() - n);
	}

	private static int find(String[] arr, String key) {
		int low = 0;
		int high = arr.length - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			int cmp = arr[mid].compareTo(key);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}
}
