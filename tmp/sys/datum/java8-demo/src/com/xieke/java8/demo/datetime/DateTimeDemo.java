package com.xieke.java8.demo.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 日期时间帮助类
 * 
 * @author 邪客
 *
 */
public class DateTimeDemo {

	public static void main(String[] args) {

		// Get the local date and local time
		final LocalDate date = LocalDate.now();
		System.out.println(date);

		// Get the local date and local time
		final LocalTime time = LocalTime.now();
		System.out.println(time);

		// Get the local date/time
		final LocalDateTime datetime = LocalDateTime.now();
		System.out.println(datetime);
	}
	
}

