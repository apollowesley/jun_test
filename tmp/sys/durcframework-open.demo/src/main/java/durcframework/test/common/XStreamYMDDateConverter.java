package durcframework.test.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * 自定义日期转换器,把日期转换成yyyy-MM-dd格式<br>
 * 注解使用方法:<br>
 * <code>
{@literal @}XStreamConverter(value=XStreamYMDDateConverter.class)
<br>private Date registDate;
	</code>
 * 
 * @author hc.tang
 * 
 */
public class XStreamYMDDateConverter extends AbstractSingleValueConverter {

	private static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Override
	public boolean canConvert(Class type) {
		return type.equals(Date.class);
	}

	@Override
	public Object fromString(String str) {
		// 这里将字符串转换成日期
		try {
			return DEFAULT_DATEFORMAT.parseObject(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		throw new ConversionException("Cannot parse date " + str);
	}

	@Override
	public String toString(Object obj) {
		// 这里将日期转换成字符串
		return DEFAULT_DATEFORMAT.format((Date) obj);
	}

}
