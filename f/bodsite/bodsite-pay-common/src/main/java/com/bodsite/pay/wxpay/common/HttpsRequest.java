package com.bodsite.pay.wxpay.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class HttpsRequest{


    private static Log log = new Log(LoggerFactory.getLogger(HttpsRequest.class));
    
    /**
     * 发送xml数据,获取返回结果
     * @param requestUrl
     * @param requestMethod
     * @param xmlStr
     * @return
     */
    public String httpXmlRequest(String requestUrl, Object xmlObj) {
        String result = null;
        String requestMethod = "POST";
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String xmlStr = xStreamForRequestPostData.toXML(xmlObj);
        Util.log("API，POST过去的数据是：");
        Util.log(xmlStr);
        try {
            HttpsURLConnection urlCon = (HttpsURLConnection) (new URL(requestUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            // 设置请求方式（GET/POST）
            urlCon.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                urlCon.connect();
            }

            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            if (null != xmlStr) {
                OutputStream outputStream = urlCon.getOutputStream();
                outputStream.write(xmlStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            InputStream inputStream = urlCon.getInputStream();
            result = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
            inputStream = null;
            urlCon.disconnect();
        } catch (MalformedURLException e) {
            log.e(e.getMessage());
        } catch (IOException e) {
        	log.e(e.getMessage());
        } catch (Exception e) {
        	log.e(e.getMessage());
        }
        return result;
    }

}
