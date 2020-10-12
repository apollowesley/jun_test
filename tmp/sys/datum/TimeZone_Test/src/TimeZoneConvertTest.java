import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author junhu
 *
 */
public class TimeZoneConvertTest
{
    final public static String DateTimeFormatString = "yyyy-MM-dd HH:mm:ss";

    final public static String UTCDateTimeFormatString = "yyyy-MM-dd'T'HH:mm:ss";

    final public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat(DateTimeFormatString);

    // PST：太平洋标准时间
    final public static SimpleDateFormat DateTimeFormatPST = new SimpleDateFormat(DateTimeFormatString);

    // UTC：协调世界时，又称世界统一时间，世界标准时间，国际协调时间
    final public static SimpleDateFormat DateTimeFormatUTC = new SimpleDateFormat(UTCDateTimeFormatString);

    static
    {
        DateTimeFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateTimeFormatPST.setTimeZone(TimeZone.getTimeZone("PST"));
    }

    public static void main(String[] args)
    {
        Date currentDate = new Date();
        System.out.println("毫秒数：" + currentDate.getTime());

        System.out.println("本地时间：" + DateTimeFormat.format(currentDate));
        System.out.println("PST/太平洋标准时间：" + DateTimeFormatPST.format(currentDate));
        System.out.println("UTC/世界标准时间：" + DateTimeFormatUTC.format(currentDate));
    }
}
