package com.lijian.devutils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络接口帮助类
 * @author sdkj
 *
 */
public class HttpUtils{
	public static final String METHOD_GET="GET";
	public static final String METHOD_POST="POST";
	public static final String METHOD_DELETE="DELETE";
	
	/**
	 * 
	 * @param apiKey
	 * @param httpUrl 请求接口
	 * @param httpArg 请求参数
	 * @param method 请求方式 
	 * 			METHOD_GET METHOD_POST 。。。
	 * @return
	 */
	public static String request(String apiKey,String httpUrl, String httpArg,String method) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        if(method.length()>0){
	        	connection.setRequestMethod(method);
	        }else{
	        	connection.setRequestMethod("GET");
	        }
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  apiKey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	/** 
     * 向指定URL发送GET方法的请求 
     * @param url  发送请求的URL 
     * @param param  请求参数，请求参数应该是name1=value1&name2=value2的形式 
     * @return URL所代表远程资源的响应 
     */  
    public static String sendGet(String url, String param) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlName = url + "?" + param;  
            URL realUrl = new URL(urlName);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            // 建立实际的连接  
            conn.connect();  
            // 获取所有响应头字段  
            Map<String, List<String>> map = conn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送GET请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    /**
     * 组织参数
     * @param mapParams
     * @return key1=val1&key2=val2 ...
     */
    public static String combineParams(Map<String,String>mapParams){
    	StringBuffer buffer=new StringBuffer();
    	for (String key : mapParams.keySet()) {
    		buffer.append(key).append("=").append(mapParams.get(key)).append("&");
    	}
    	return buffer.substring(0, buffer.length()-1);
    }
	
    public static void main(String[] args) {
		Map<String,String>map=new HashMap<String,String>();
		/* 1.用户导入多说*/
//		map.put("short_name", "lizi2");
//		map.put("secret", "6010048695604a0bd409a0403ab2e941");
//		map.put("access_token", "4c51d2f73fd6bc8a1cd8fcf8f5f2bba9");
//		map.put("user[user_key]", "id123");
//		map.put("user[name]", "nickname双双");
//		
//		String result=sendPost("http://api.duoshuo.com/sites/join.json", combineParams(map));
//		System.out.println(result);
		 
		/* 2.获取 access_token */
		map.put("client_id", "lizi2");
		map.put("code", "431b380a9731b1c36a3a63e53afee005");
//		map.put("secret", "6010048695604a0bd409a0403ab2e941");
//		map.put("access_token", "4c51d2f73fd6bc8a1cd8fcf8f5f2bba9");
//		map.put("user[user_key]", "id123");
//		map.put("user[name]", "nickname双双");
//		
		String result=sendPost("http://api.duoshuo.com/oauth2/access_token", combineParams(map));
		System.out.println(result);
		
		
		
	}
    
    /*
     注册方法：
     	用户注册的时候注册到本系统的同时注册到 多说  使用 import
     
     登录回调方法：（这个方法写到 login 回调）
    	这里获取 code ,	通过code 和 client_id(short_name) 获取 access_token  post //  http://api.duoshuo.com/oauth2/access_token

    	使用 user_id 和 access_token 获取 用户多说信息   get // http://api.duoshuo.com/users/profile.json?user_id=378333
    	
    	判断是不是注册了本系统，如果没有注册，注册到本系统，将本系统的信息和多说信息 合并同步到多说 参考注释 1
    	
    	如果注册了本系统，登录该账号 
    	
    退出回调方法：
    	退出本系统账号
    	
 我的登录：
 	var u=new CookieUtils(); 
	u.setCookie("duoshuo_token",cookieVal);  
 我的退出：
 	 u.delCookie("duoshuo_token");
    	
     */
    
//	public static void main(String[] args) {
//		String apiKey="991bb8fbb66a07ae828ff130c7785cab";
//		String httpUrl = "http://www.tuling123.com/openapi/api";
//		String httpArg = "key=991bb8fbb66a07ae828ff130c7785cab&info=你好"; 
//		String jsonResult = request(apiKey,httpUrl, httpArg,METHOD_GET);
//		System.out.println(jsonResult);
//	}

}
