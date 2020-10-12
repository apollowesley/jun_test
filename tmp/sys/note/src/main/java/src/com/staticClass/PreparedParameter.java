package com.staticClass;

public class PreparedParameter {
	public static String setString(String x) {
		return setString(x, "UTF-8");
	}

	public static String setString(String x, String charType) {
		// if the passed string is null, then set this column to null
		if (x == null) {
			return "";
		} else {
			StringBuffer buf = new StringBuffer((int) (x.length() * 1.1));
			buf.append('\'');

			int stringLength = x.length();

			//
			// Note: buf.append(char) is _faster_ than
			// appending in blocks, because the block
			// append requires a System.arraycopy()....
			// go figure...
			//
			for (int i = 0; i < stringLength; ++i) {
				char c = x.charAt(i);

				switch (c) {
				case 0: /* Must be escaped for 'mysql' */
					buf.append('\\');
					buf.append('0');

					break;

				case '\n': /* Must be escaped for logs */
					buf.append('\\');
					buf.append('n');

					break;

				case '\r':
					buf.append('\\');
					buf.append('r');

					break;

				case '\\':
					buf.append('\\');
					buf.append('\\');

					break;

				case '\'':
					buf.append('\\');
					buf.append('\'');

					break;

				case '"': /* Better safe than sorry */
					buf.append('\\');
					buf.append('"');

					break;

				case '\032': /* This gives problems on Win32 */
					buf.append('\\');
					buf.append('Z');

					break;

				default:
					buf.append(c);
				}
			}

			buf.append('\'');

			String parameterAsString = buf.toString();

			byte[] parameterAsBytes = null;

			// Send with platform character encoding
			try {
				parameterAsBytes = parameterAsString.getBytes(charType);
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return new String(parameterAsBytes);
		}
	}
}