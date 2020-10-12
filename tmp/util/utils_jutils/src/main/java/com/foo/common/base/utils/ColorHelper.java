package com.foo.common.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Color utils.
 * 
 * @author Steve
 *
 */
public class ColorHelper {
	public static final Logger logger = LoggerFactory
			.getLogger(ColorHelper.class);

	public static void main(String[] args) {
		String color = "#1c84c6";
		logger.info("hex2Rgb result is:{}", hex2Rgb(color));
		logger.info("rgb2Hex result is:{}", rgb2Hex(new int[] { 28, 31, 135 }));
	}

	public static int[] hex2Rgb(String color) {

		color = color.replace("#", "");

		int r = Integer.parseInt(color.substring(0, 2), 16);
		int g = Integer.parseInt(color.substring(2, 4), 16);
		int b = Integer.parseInt(color.substring(4, 6), 16);

		return new int[] { r, g, b };

	}

	public static String rgb2Hex(int[] rgb) {

		String reuslt = "#";
		for (int color : rgb) {
			reuslt += Integer.toHexString(color);
		}
		return reuslt.toUpperCase();

	}
}
