package common.framework.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {
	/**
	 * Get minutes from 1900-1-1 00:00:00 to the specified
	 * year,month,date,hour,minute.
	 * 
	 * @param year
	 *            (>=1900)
	 * @param month
	 *            (1-12)
	 * @param date
	 *            (1-31)
	 * @param hour
	 *            (0-23)
	 * @param minute
	 *            (0-59)
	 * @return (0,1,2,3,...)
	 */
	public static long getMinutes(int year, int month, int date, int hour, int minute) {
		return getHours(year, month, date, hour) * 60 + minute;
	}

	/**
	 * get how many minutes from '1900-1-1' to time specified in
	 * timeInMilliSeconds.
	 * 
	 * @param timeInMilliSeconds
	 *            the timeInMilliSeconds is from 1970-1-1 00:00:00
	 * @return (0,1,2,3,...)
	 */
	public static long getMinutes(long timeInMilliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliSeconds);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return getMinutes(year, month, date, hour, minute);
	}

	/**
	 * Get how many hours from 1900-1-1 00:00:00 to the specific
	 * year,month,date,hour.
	 * 
	 * @param year
	 *            (>=1900)
	 * @param month
	 *            (1-12)
	 * @param date
	 *            (1-31)
	 * @param hour
	 *            (0-23)
	 * @return long (0,1,2,3,...)
	 */
	public static long getHours(int year, int month, int date, int hour) {
		return getDays(year, month, date) * 24 + hour;
	}

	/**
	 * get how many hours from '1900-1-1' to time specified in timeInMillis.
	 * 
	 * @param timeInMilliSeconds
	 *            the timeInMilliSeconds is from 1970-1-1 00:00:00
	 * @return (0,1,2,3,...)
	 */
	public static long getHours(long timeInMilliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliSeconds);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		return getHours(year, month, date, hour);
	}

	/**
	 * Get how many days from 1900-1-1 to the specific year,month,date.
	 * 
	 * @param year
	 *            (>=1900)
	 * @param month
	 *            (1-12)
	 * @param date
	 *            (1-31)
	 * @return long (0,1,2,3,...)
	 */
	public static long getDays(int year, int month, int date) {
		int leapYear = 0;
		int commonYear = 0;
		int daysOfMonth = 0;
		for (int i = 1900; i < year; i++) {
			if (isLeapYear(i)) {
				leapYear++;
			} else {
				commonYear++;
			}
		}
		for (int i = 1; i < month; i++) {
			if (i == 4 || i == 6 || i == 9 || i == 11) {
				daysOfMonth += 30;
			} else if (i == 2) {
				if (isLeapYear(year)) {
					daysOfMonth += 29;
				} else {
					daysOfMonth += 28;
				}
			} else {
				daysOfMonth += 31;
			}
		}
		return leapYear * 366 + commonYear * 365 + daysOfMonth + date - 1;
	}

	/**
	 * get how many days from '1900-1-1' to time specified in timeInMillis.
	 * 
	 * @param timeInMilliSeconds
	 *            the timeInMilliSeconds is from 1970-1-1 00:00:00
	 * @return long days
	 */
	public static long getDays(long timeInMilliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliSeconds);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		return getDays(year, month, date);
	}

	/**
	 * Get how many months from 1900-1-1 to the specific year,month.
	 * 
	 * @param year
	 *            (>=1900)
	 * @param month
	 *            (1-12)
	 * @return int (0,1,2,3,...)
	 */
	public static int getMonths(int year, int month) {
		return (year - 1900) * 12 + month - 1;
	}

	public static int getMonths(long timeInMilliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMilliSeconds);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return getMonths(year, month);
	}

	/**
	 * Get weeks from 1900-1-1 to the specific year,month,date.
	 * 
	 * @param year
	 *            (>=1900)
	 * @param month
	 *            (1-12)
	 * @param date
	 *            (1-31).
	 * @return int
	 */
	public static int getWeeks(int year, int month, int date) {
		return (int) (getDays(year, month, date) / 7);
	}

	public static int getWeeks(long timeInMilliSeconds) {
		return (int) (getDays(timeInMilliSeconds) / 7);
	}

	public static Date minutes2Date(long minutes) {
		long minutesdiff = minutes - getMinutes(1970, 1, 1, 0, 0);
		int hOffset = TimeZone.getDefault().getRawOffset();
		return new Date(minutesdiff * 60 * 1000 - hOffset);
	}

	/**
	 * Get date of the giving hours from which 1900-1-1 00:00:00
	 * 
	 * @param hours
	 *            (0,1,2,...) .
	 * @return Date
	 */
	public static Date hours2Date(long hours) {
		long days = hours / 24 - getDays(1970, 1, 1);
		int hOffset = TimeZone.getDefault().getRawOffset();
		int hour = (int) (hours % 24);
		return new Date((days * 24 + hour) * 60 * 60 * 1000 - hOffset);
	}

	/**
	 * Get date of the giving days from which 1900-1-1.
	 * 
	 * @param days
	 *            .
	 * @return Date
	 */
	public static Date days2Date(long days) {
		int hOffset = TimeZone.getDefault().getRawOffset();
		return new Date((days - getDays(1970, 1, 1)) * 24 * 60 * 60 * 1000 - hOffset);
	}

	/**
	 * Get date of the giving weeks from which 1900-1-1.
	 * 
	 * @param weeks
	 *            .
	 * @return Date
	 */
	public static Date weeks2Date(int weeks) {
		return new Date((weeks * 7 - getDays(1970, 1, 1)) * 24 * 60 * 60 * 1000);
	}

	/**
	 * Get date of the giving months which from 1900-1.
	 * 
	 * @param months
	 *            .
	 * @return Date
	 */
	public static Date months2Date(int months) {
		int year = 1900 + months / 12;
		int month = months % 12 + 1;
		long days = getDays(year, month, 1);
		return days2Date(days);
	}

	/**
	 * Check a year is a leap year or not.
	 * 
	 * @param year
	 *            .
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		}
		return false;
	}

	public static String currentTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public static String timeConsumed(long startTime) {
		long now = System.currentTimeMillis();
		long gap = now - startTime;
		if (gap <= 0) {
			return "0 hours 0 minutes 0 seconds.";
		}
		long hours = gap / (1000 * 60 * 60);
		long minutes = (gap / (1000 * 60)) % 60;
		long seconds = (gap / 1000) % 60;
		StringBuffer sb = new StringBuffer();
		sb.append(hours).append(" hours ").append(minutes).append(" minutes ").append(seconds).append(" seconds.");
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long timeMills = System.currentTimeMillis();

		long minutes = getMinutes(1900, 1, 1, 0, 14) / 15;

		System.out.println("minutes=" + minutes);

		long fifts = minutes / 15;

		System.out.println("fifts=" + fifts);

		System.out.println("minutes2date=" + minutes2Date(fifts * 15));

		long days = getDays(timeMills);
		long hours = getHours(timeMills);
		long weeks = getWeeks(timeMills);

		System.out.println("days=" + days);
		System.out.println("hours=" + hours);
		System.out.println("weeks=" + weeks);

		System.out.println("days2date=" + days2Date(days));
		System.out.println("hours2Date=" + hours2Date(989467));
		System.out.println("week2date=" + days2Date(weeks * 7));
		System.out.println("fift2date=" + minutes2Date(3922184 * 15));
		System.out.println("fift2date=" + minutes2Date(3922209 * 15));

		long nano = 9652994762128203l;
		Date myDate = new Date(9652994762l);
		System.out.println(myDate);
		System.out.println(nano);
		System.out.println(System.nanoTime());
	}
}
