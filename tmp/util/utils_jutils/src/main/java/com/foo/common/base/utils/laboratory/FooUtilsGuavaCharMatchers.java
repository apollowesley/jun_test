package com.foo.common.base.utils.laboratory;

import com.google.common.base.CharMatcher;

public class FooUtilsGuavaCharMatchers {
	public static void main(String[] args) {
		System.out.println(CharMatcher.DIGIT.collapseFrom("f123uck", '-'));// f-uck
		System.out.println(CharMatcher.DIGIT.matchesAllOf("12.213"));// f-uck
		System.out.println(CharMatcher.anyOf("asdanxz").removeFrom(
				"asdanxzchasnmo	qewuirewqyuidsad"));// f-uck
	}
}
