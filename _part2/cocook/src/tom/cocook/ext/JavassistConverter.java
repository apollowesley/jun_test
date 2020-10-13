package tom.cocook.ext;

import java.sql.Timestamp;
import java.util.Date;

import tom.kit.converter.Converter;

public class JavassistConverter {

	public static String getStr(Object val) {
		if (String.class == val.getClass())
			return (String) val;
		return Converter.coverterClass2Value(String.class, null, val);
	}

	public static int getInt(Object val) {
		if (Converter.convertType2Class(val.getClass()) == Integer.class)
			return (Integer) val;
		return Converter.coverterClass2Value(Integer.class, null, val.toString());
	}

	public static double getDouble(Object val) {
		if (Converter.convertType2Class(val.getClass()) == Double.class)
			return (Double) val;
		return Converter.coverterClass2Value(Double.class, null, val.toString());
	}

	public static double getFloat(Object val) {
		if (Converter.convertType2Class(val.getClass()) == Float.class)
			return (Float) val;
		return Converter.coverterClass2Value(Float.class, null, val.toString());
	}

	public static double getLong(Object val) {
		if (Converter.convertType2Class(val.getClass()) == Long.class)
			return (Long) val;
		return Converter.coverterClass2Value(Long.class, null, val.toString());
	}

	public static double getShort(Object val) {
		if (Converter.convertType2Class(val.getClass()) == Short.class)
			return (Short) val;
		return Converter.coverterClass2Value(Short.class, null, val.toString());
	}

	public static Date getDate(Object val) {
		return Converter.coverterClass2Value(Date.class, null, val.toString());
	}

	public static Timestamp getTime(Object val) {
		return Converter.coverterClass2Value(Timestamp.class, null, val.toString());
	}

}
