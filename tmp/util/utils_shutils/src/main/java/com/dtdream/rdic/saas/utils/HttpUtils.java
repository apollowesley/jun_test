package com.dtdream.rdic.saas.utils;

import com.dtdream.rdic.saas.def.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ozz8 on 2015/07/14.
 */
public class HttpUtils {
    protected final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static String url(String url, Map<String,Object> params) {
        if (null == url)
            return null;

        StringBuilder sb = new StringBuilder(url);

        if (! url.endsWith("?"))
            sb.append("?");

        if (null != params) {
            Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
            Map.Entry<String,Object> item;
            while (it.hasNext()) {
                item = it.next();
                sb.append(item.getKey()).append("=").append(item.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static void send (URLConnection connection, byte[] data) {
        if (null == connection) {
            logger.error("获取请求包头失败");
            return ;
        }
        OutputStream out = null;
        try {
            out = connection.getOutputStream();
            out.write(data);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }

    public static void send (URLConnection connection, File file) {
        if (null == connection) {
            logger.error("获取HTTP统一请求包头失败");
            return;
        }
        OutputStream ostream = null;
        FileInputStream istream = null;
        try {
            ostream = connection.getOutputStream();
            istream = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = istream.read(buf)) != -1) {
                ostream.write(buf, 0, len);
            }
            ostream.flush();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            try {
                if (null != ostream)
                    ostream.close();
                if (null != istream)
                    istream.close();
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }

    public static String receive (URLConnection connection) {
        BufferedReader br = null;
        StringBuilder objStrBuilder = new StringBuilder();
        String objStr;

        if (null == connection) {
            logger.error("连接不存在");
            return Constant.EMPTY_STRING;
        }

        try {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            while ((objStr = br.readLine()) != null) {
                objStrBuilder.append(objStr);
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            try {
                if (null != br)
                    br.close();
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
        }

        return objStrBuilder.toString();
    }

    public static URLConnection connect (String target, RequestMethod method, Map<String,String> headers) {
        if (null == target) {
            logger.error("URL为空或不存在");
            return null;
        }

        URL url = null;
        try {
            url = new URL(target);
        } catch (MalformedURLException e) {
            return null;
        }

        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            return null;
        }

        connection.setDoInput(true);
        if (method.equals(RequestMethod.POST)) {
            connection.setDoOutput(true);
        }

        connection.setRequestProperty("Pragma:", "no-cache");
        connection.setRequestProperty("Cache-Control", "no-cache");

        if (null != headers) {
            Iterator<Map.Entry<String, String>> it = headers.entrySet().iterator();
            Map.Entry<String,String> item;
            while (it.hasNext()) {
                item = it.next();
                connection.setRequestProperty(item.getKey(), item.getValue());
            }
        }

        return connection;
    }


    public static String post (String url, Map<String,String> headers, byte[] data) {
        URLConnection connection = connect(url, RequestMethod.POST, headers);
        if (null != connection) {
            send(connection, data);
            return receive(connection);
        }
        return null;
    }

    public static String get (String url, Map<String,String> headers, Map<String,Object> params) {
        String target = url(url, params);
        URLConnection connection = connect(target, RequestMethod.GET, headers);
        if (null != connection) {
            return receive(connection);
        }
        return null;
    }

    public static boolean match (HttpServletRequest request, String header, String expected) {
        if (KitUtils.check(header).failure())
            return false;
        String hdr = request.getHeader(header);
        return null != hdr ? hdr.indexOf(expected) >= 0 : false;
    }

}
