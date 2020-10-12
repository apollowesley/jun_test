package com.mingsoft.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.MD5Util;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.SpringUtil;

class AccessToken {

	private int expiresIn;

	private String token;

	public int getExpiresIn() {
		return expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

class Config {

	private String appId; // 公众号appId
	private String nonceStr; // 随机字串
	private String signature;// 获取到的签名
	private String ticket; // 获取到的凭证
	private String timestamp;// 时间戳

	public String getAppId() {
		return appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public String getTicket() {
		return ticket;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}

class MyX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}

class Ticket {

	private int expiresIn;// 凭证有效时间,单位：秒
	private String ticket;// 获取到的凭证

	public int getExpiresIn() {
		return expiresIn;
	}

	public String getTicket() {
		return ticket;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}

/**
 * 微信项目
 * 
 * @author 铭飞开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2016年9月4日<br/>
 *          历史修订：<br/>
 */
public class WeixinUtil {

	public static Logger LOG = Logger.getLogger(WeixinUtil.class);

	// 获取access_token的接口地址(GET) 限200次/天
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取jsapi_ticket的接口地址(GET)
	public static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				LOG.info("获取token失败 errcode:{} errmsg:{}" + jsonObject.getInteger("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 按照微信的需求生成支付key
	 * 
	 * @param params
	 *            参数
	 * @param key
	 *            商户交易key
	 * @return 支付key
	 */
	public static String getPaySign(Map<String, String> params, String key) {
		// 签名步骤一：按字典序排序参数
		Map<String, String> temp = StringUtil.sortMapByKey(params);
		// 签名步骤二：在string后加入KEY
		String sign = StringUtil.buildUrl("", temp).replace("?", "") + "&key=" + key;
		// 签名步骤三：MD5加密
		sign = MD5Util.MD5Encode(sign, "utf8");
		// 签名步骤四：所有字符转为大写
		sign = sign.toUpperCase();
		return sign;
	}

	/**
	 * 获取ticket
	 * 
	 * @param token
	 *            调用getAccessToken可获得
	 * @param type
	 * @return
	 */
	public static Ticket getTicket(String token, String type) {
		Ticket ticket = null;
		String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", token).replace("TYPE", type);
		LOG.debug("ticket url:" + requestUrl);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				ticket = new Ticket();
				ticket.setTicket(jsonObject.getString("ticket"));
				ticket.setExpiresIn(jsonObject.getInteger("expires_in"));
			} catch (Exception e) {
				ticket = null;
				// 获取token失败
				LOG.info("获取ticket失败 errcode:{} errmsg:{}" + jsonObject.getInteger("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return ticket;
	}

	/**
	 * 获取签名配置信息
	 * 
	 * @param appId
	 *            微信 appid
	 * @param appsecret
	 *            微信密钥
	 * @param type
	 *            ticket类型
	 * @return
	 */
	public static String getSignatureConfig(String appId, String appsecret, String type, String url) {
		EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringUtil.getBean(EhCacheCacheManager.class);
		org.springframework.cache.Cache cache = cacheManager.getCache("weixin");

		String nonceStr = StringUtil.randomNumber(16);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		ValueWrapper vw = cache.get("ticket");
		String ticket = "";
		if (vw == null || vw.get() == null) {
			// 获取access_token
			String token = WeixinUtil.getAccessToken(appId, appsecret).getToken();
			// access_token换取jsapi_ticket
			ticket = WeixinUtil.getTicket(token, type).getTicket();
			cache.put("ticket", ticket);
			LOG.debug("缓存ticket:" + ticket);
		} else {
			ticket = vw.get().toString();
			LOG.debug("读取缓存ticket:" + ticket);
		}
		// 字典排序的签名参数
		String params = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
		params = String.format(params, ticket, nonceStr, timestamp, url);
		// 对string1进行sha1签名，得到signature
		LOG.debug("params" + params);
		String signature = org.apache.commons.codec.digest.DigestUtils.shaHex(params);
		LOG.debug("signature:" + signature);

		// 最后组装json数据
		Config config = new Config();
		config.setAppId(appId);
		config.setTicket(ticket);
		config.setNonceStr(nonceStr);
		config.setSignature(signature);
		config.setTimestamp(timestamp);
		return JSONObject.toJSONString(config);
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式(GET/POST)
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式(GET/POST)
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式,防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			LOG.info("Weixin server connection timed out.");
		} catch (Exception e) {
			LOG.info("信任管理器请求时..." + e);
		}
		return jsonObject;
	}
}