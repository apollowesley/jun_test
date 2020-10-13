package com.autoscript.ui.helper;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JFrame;

public class UIPropertyHelper {
	private static ResourceBundle bundle = null;

	private static final ResourceBundle getResourceBundle() {
		if (bundle == null)
			bundle = ResourceBundle.getBundle("com.autoscript.ui.message.ui");
		return bundle;
	}

	public static String getString(String key) {
		String value = null;
		try {
			value = getResourceBundle().getString(key);
		} catch (MissingResourceException e) {
			System.out
					.println("java.util.MissingResourceException: Couldn't find value for: "
							+ key);
		}
		if (value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
	}

	public static char getMnemonic(String key) {
		return getString(key).charAt(0);
	}

	public static void centerFrame(JFrame frame) {
		JFrame f = frame;
		Rectangle screenRect = f.getGraphicsConfiguration().getBounds();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				f.getGraphicsConfiguration());
		int centerWidth = screenRect.width >= f.getSize().width ? screenRect.x
				+ screenRect.width / 2 - f.getSize().width / 2 : screenRect.x;
		int centerHeight = screenRect.height >= f.getSize().height ? screenRect.y
				+ screenRect.height / 2 - f.getSize().height / 2
				: screenRect.y;
		centerHeight = centerHeight >= screenInsets.top ? centerHeight
				: screenInsets.top;
		f.setLocation(centerWidth, centerHeight);
	}

	/**
	 * 带参数的获取消息名称
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getString(String key, Object... params) {
		try {
			String value = getString(key);
			return MessageFormat.format(value, params);
		} catch (Exception e) {
			e.printStackTrace();
			return key;
		}

	}
}
