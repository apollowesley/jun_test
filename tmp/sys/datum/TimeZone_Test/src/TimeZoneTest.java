import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author junhu
 *
 */
public class TimeZoneTest
{
    @SuppressWarnings("all")
    public static void main(String[] args)
    {
        // 获得所有时区ID
        getAllTimeZoneID();
        // 获取本地默认时区
        getDefaultTimeZone();

        Date d = new Date();
        System.out.println("Date.toGMTString():" + d.toGMTString());// 世界时UT即格林尼治时间，格林尼治所在地的标准时间
        System.out.println("Date.toLocaleString():" + d.toLocaleString());
    }

    /**
     * 获得支持的时区信息
     */
    public static void getAllTimeZoneID()
    {
        String[] ids = TimeZone.getAvailableIDs();
        System.out.println("TimeZone AvailableIDs Numbers :" + ids.length);
        for (String id : ids)
        {
            System.out.println("TimeZone:Id-" + id);
        }
    }

    /**
     * 获取本地默认时区
     */
    public static void getDefaultTimeZone()
    {
        System.out.println("TimeZone.getDefault():" + TimeZone.getDefault());
    }

}
