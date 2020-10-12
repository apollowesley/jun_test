package site.zhouinfo.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 获取html
 *
 * @author zhouinfo
 * @Create Date 2016-08-18 22:25
 */
public class GetHtmlString {
    public static String getHTML(String s) {
        BufferedReader br = null;
        final StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(s);
            br = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));
            String str = "";
            while (null != (str = br.readLine())) {
                sb.append(str).append("\r\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
