package cn.backflow.lib.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class WebUtil {

    protected static Logger log = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 把地址栏形式的参数字符串转换成Map
     *
     * @param query 参数字符串
     */
    public static Map<String, String> queryStringToMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            if (!param.contains("=")) {

                continue;
            }
            String name = param.substring(0, param.indexOf("="));
            String value = param.substring(param.indexOf("=") + 1);
            if (map.get(name) != null)
                map.put(name, map.get(name) + "," + value);
            else
                map.put(name, value);
        }
        return map;
    }

    public String getContent(ServletRequest request) {
        String encoding = getEncoding(request.getContentType());
        if (encoding == null) {
            encoding = "UTF-8";
        }
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), encoding));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public String getEncoding(String contentType) {
        String[] headers = contentType.split(";");
        for (String header : headers) {
            String[] params = header.split("=");
            if (params.length == 2) {
                if (params[0].equals("charset")) {
                    return params[1];
                }
            }
        }
        return null;
    }

    public static Map<String, String> getRequestAllParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            // 解决乱码，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(e.getKey(), StringUtils.join(e.getValue(), ","));
        }
        return params;
    }

    public static String getRequestUrl(HttpServletRequest request) {
        StringBuffer sb = request.getRequestURL();
        String query = request.getQueryString();
        if (query != null) {
            sb.append("?").append(query);
        }
        return sb.toString();
    }

    /**
     * 获取POST与GET请求中的所有参数
     */
    public static String requestAllParamsAsQueryString(HttpServletRequest request) {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            for (String v : e.getValue()) {
                query.append("&").append(e.getKey()).append("=").append(v);
            }
        }
        return query.toString();
    }

    public static String getContent(String url) {
        return getContent(url, "utf-8");
    }

    public static String getContent(String pageUrl, String encoding) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(pageUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 判断该请求是否为AJAX请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
    }

    public static boolean isWebSocketRequest(HttpServletRequest request) {
        return "websocket".equals(request.getHeader("upgrade"));
    }

    /**
     * 判断请求是否通过点击链接进入 (否则为输入网址进入)
     */
    public static boolean isFromClickUrl(HttpServletRequest request) {
        String refer = request.getHeader("Referer");
        String host = request.getHeader("Host");
        if (refer == null || "".equals(refer)) return false; // 此情况为在浏览器中输入地址

        String[] arr = refer.split("//");
        if (arr.length > 1) {
            String tmpHost = arr[1].substring(0, arr[1].indexOf("/"));
            return host.equals(tmpHost); // 判断是否是从我们的主机发起的请求
        }
        return false;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(remoteAddr))
            remoteAddr = request.getHeader("X-Forwarded-For");
        else if (StringUtils.isNotBlank(remoteAddr))
            remoteAddr = request.getHeader("DynamicProxyTest-Client-IP");
        else if (StringUtils.isNotBlank(remoteAddr))
            remoteAddr = request.getHeader("WL-DynamicProxyTest-Client-IP");
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 将localhost和127.0.0.1转换为服务器上的ip地址
     */
    public static String getLocalAddr(String url) {
        if (url.toLowerCase().contains("localhost") || url.toLowerCase().contains("127.0.0.1")) {
            try {
                InetAddress localhost = InetAddress.getLocalHost();
                url = url.replaceAll("(localhost)|(127.0.0.1)", localhost.getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public static void jsonResponse(ServletResponse response, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(JsonUtil.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        try {
            if (url.contains("https://")) {
                return sendGetHTTPS(url, param);
            } else {
                return sendGetHTTP(url, param);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGetHTTP(String url, String param) {
        log.debug("Sending get request: {} {}", url, param);
        StringBuilder builder = new StringBuilder();
        BufferedReader in = null;
        try {
            String URL = url;
            if (!StringUtil.isEmpty(param)) {
                param = param.startsWith("?") ? param : "?" + param;
                URL = url + param;
            }
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestMethod("GET");
            // 建立实际的连接
            conn.connect();
            // 遍历所有的响应头字段
            // for (String key : conn.getHeaderFields().keySet()) {
            //    System.out.println(key + "--->" + map.get(key));
            // }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return builder.toString();
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGetHTTPS(String url, String param) {
        log.debug("Sending get request: {} {}", url, param);
        StringBuilder builder = new StringBuilder();
        BufferedReader in = null;
        try {
            String URL = url;
            if (!StringUtil.isEmpty(param)) {
                param = param.startsWith("?") ? param : "?" + param;
                URL = url + param;
            }
            // 打开和URL之间的连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(URL).openConnection();
            trustAllHosts();
            // 设置通用的请求属性
            // conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
            conn.setRequestProperty("content-type", "text/html");
            conn.setRequestMethod("GET");
            // 建立实际的连接
            conn.connect();
            // 遍历所有的响应头字段
            // for (String key : conn.getHeaderFields().keySet()) {
            //    System.out.println(key + "--->" + map.get(key));
            // }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return builder.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param map 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map map) {
        if (url.contains("https://")) {
            return sendPostHTTPS(url, StringUtil.transMapToString(map));
        } else {
            return sendPostHTTP(url, StringUtil.transMapToString(map));
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostHTTP(String url, String param) {
        log.info("Sending post request: {} {}", url, param);
        StringBuilder builder = new StringBuilder();
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return builder.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostHTTPS(String url, String param) {
        log.info("Sending post request: {} {}", url, param);
        StringBuilder builder = new StringBuilder();
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 打开和URL之间的连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            trustAllHosts();
            // 设置通用的请求属性
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
//            conn.setRequestProperty("content-type", "text/html");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return builder.toString();
    }

    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }
        }
        };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Device getDevice(HttpServletRequest request) {
        return new Device(request.getHeader("User-Agent"));
    }

    /**
     * 移动设备检测类
     * Create by Nandy.h on 2016/8/4
     */
    public static class Device {

        public String os;
        public String osVersion;
        public boolean android;
        public boolean androidChrome;
        public boolean ios;
        public boolean ipad;
        public boolean ipod;
        public boolean iphone;
        public boolean weixin;
        public boolean webView;

        public Device(String userAgent) {

            Matcher mAndoird = Pattern.compile("(Android);?[\\s/]+([\\d.]+)?").matcher(userAgent);
            Matcher mIpad = Pattern.compile("(iPad).*OS\\s([\\d_]+)").matcher(userAgent);
            Matcher mIpod = Pattern.compile("(iPod)(.*OS\\s([\\d_]+))?").matcher(userAgent);
            Matcher mIphone = Pattern.compile("(iPhone\\sOS)\\s([\\d_]+)").matcher(userAgent);

            // Android
            if (mAndoird.find()) {
                os = "android";
                android = true;
                osVersion = mAndoird.group(2);
                androidChrome = userAgent.toLowerCase().contains("chrome");
            }

            if (mIphone.find() && !mIpad.find()) {
                iphone = true;
            }

            if (mIpad.find() || mIpod.find() || mIphone.find()) {
                ios = true;
                os = "ios";
            }

            // IOS
            if (mIphone.find() && !ipod) {
                osVersion = mIphone.group(2).replaceAll("_", ".");
                iphone = true;
            }
            if (mIpad.find()) {
                osVersion = mIpad.group(2).replaceAll("_", ".");
                ipad = true;
            }
            if (mIpod.find()) {
                osVersion = mIpod.group(3).replaceAll("_", ".");
                iphone = true;
                ipod = true;
            }

            // iOS 8+ changed UA
            if (ios && osVersion != null && userAgent.matches("/.*AppleWebKit(?!.*Safari)/i")) {
                if ("10".equals(osVersion.split(".")[0]))
                    osVersion = userAgent.toLowerCase().split("version/")[1].split(" ")[0];
            }

            // Webview
            if ((iphone || ipad || ipod) && userAgent.matches("/.*AppleWebKit(?!.*Safari)/i")) {
                webView = true;
            }

            weixin = userAgent.contains("MicroMessenger");
        }
    }

}