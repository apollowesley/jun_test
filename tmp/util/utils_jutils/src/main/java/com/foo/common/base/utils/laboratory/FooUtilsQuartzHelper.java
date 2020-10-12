package com.foo.common.base.utils.laboratory;

import org.joda.time.DateTime;
import org.junit.Test;

import com.foo.common.base.utils.FooUtils;

public class FooUtilsQuartzHelper {
	public static String getCronExpressionFromDateTime(DateTime myDateTime) {
		return "" + myDateTime.getSecondOfMinute() + " "
				+ myDateTime.getMinuteOfHour() + " "
				+ myDateTime.getHourOfDay() + " " + myDateTime.getDayOfMonth()
				+ " " + myDateTime.getMonthOfYear() + " ? "
				+ myDateTime.getYear() + "";
	}

	@Test
	public void testGetCronExpressionFromDateTime() {
		System.out.println(FooUtils.getOracleSearchFragment(new DateTime()));
	}

}
