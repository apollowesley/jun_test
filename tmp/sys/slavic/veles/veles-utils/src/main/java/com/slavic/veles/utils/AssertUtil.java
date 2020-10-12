package com.slavic.veles.utils;


import com.slavic.veles.base.exception.ApplyException;
import com.slavic.veles.base.response.EntryHeader;

import java.util.function.Predicate;

public class AssertUtil {

	public static <T> void assertTrue(boolean condition, String falseDefaultMessage) {
		assertTrue(condition,EntryHeader.APPLY_ERROR(falseDefaultMessage));
	}

	public static <T> void assertTrue(boolean condition, EntryHeader entryHeader) {
		if (!condition) {
			throw new ApplyException(entryHeader);
		}
	}

	public static <T> void assertFalse(boolean condition, String trueDefaultMessage) {
		assertFalse(condition, EntryHeader.APPLY_ERROR(trueDefaultMessage));
	}

	public static <T> void assertFalse(boolean condition, EntryHeader entryHeader) {
		if (condition) {
			throw new ApplyException(entryHeader);
		}
	}

	public static <T> void assertMatch(Predicate<? super T> predicate, T object, EntryHeader entryHeader) {
		if (!predicate.test(object)) {
			throw new ApplyException(entryHeader);
		}
	}

	public static <T> void assertNoMatch( Predicate<? super T> predicate, T object, EntryHeader entryHeader) {
		if (predicate.test(object)) {
			throw new ApplyException(entryHeader);
		}
	}

	@SafeVarargs
	public static <T> void assertAllMatch(EntryHeader entryHeader, String cause, Predicate<? super T> predicate, T... objects) {
		for (T object : objects) {
			if (!predicate.test(object)) {
				throw new ApplyException(entryHeader);
			}
		}
	}

	@SafeVarargs
	public static <T> void assertAnyMatch(EntryHeader entryHeader, String cause, Predicate<? super T> predicate, T... objects) {
		boolean anyMatch = false;
		for (T object : objects) {
			if (predicate.test(object)) {
				anyMatch = true;
				break;
			}
		}
		if (!anyMatch) {
			throw new ApplyException(entryHeader);
		}
	}
}
