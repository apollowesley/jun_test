package site.zhouinfo.network;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author zhouinfo
 * @Create Date 2016-08-18 17:28
 */
public class GetLocalIPAddress {
    public static void main(String[] args) {
        try {
            String ip1 = getMyIP();
            System.out.println("公网:" + ip1);
            String ip2 = getMyIPLocal();
            System.out.println("内网:" + ip2);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static String getMyIP() {
        String s = "http://1212.ip138.com/ic.asp";
        String webContent = GetHtmlString.getHTML(s);
        int start = webContent.indexOf("[") + 1;
        int end = webContent.indexOf("]");
        int from = webContent.indexOf("</center>");
        return webContent.substring(start, end) + webContent.substring(end + 1, from);
    }

    public static String getMyIPLocal() throws IOException {
        InetAddress ia = InetAddress.getLocalHost();
        return ia.getHostAddress();
    }

}
