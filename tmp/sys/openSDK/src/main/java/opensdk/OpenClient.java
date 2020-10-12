package opensdk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import opensdk.util.OpenUtil;
import opensdk.util.PostUtil;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 请求客户端
 * @author hc.tang
 *
 */
public class OpenClient {
	
	private static Logger logger = Logger.getLogger(OpenClient.class);
	
	private String appId;
	private String secret;
	private String serverCtx = "";

	private String appIdName = "appId";
	private String signName = "sign";
	private String timestampName = "timestamp";

	public OpenClient(String appId, String secret, String serverCtx) {
		this.appId = appId;
		this.secret = secret;
		this.serverCtx = serverCtx;
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param params
	 * @return 返回XML数据
	 */
	public String postXML(String url,Map<String, String> params) {
		return this.post(url, params, PostUtil.XML);
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param entity
	 * @return 返回XML数据
	 */
	public String postXML(String url, Object entity) {
		Map<String, String> paramsMap = OpenUtil.buildParamsMap(entity);
		return this.postXML(url, paramsMap);
	}
	
	
	/**
	 * 发送请求
	 * @param url
	 * @param params
	 * @return 返回JSON数据
	 */
	public String postJsonByMap(String url,Map<String, String> params,Locale locale) {
		return this.post(url, params,PostUtil.JSON,locale);
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param entity
	 * @return 返回JSON数据
	 */
	public String postJsonByObj(String url, Object entity,Locale locale) {
		Map<String, String> paramsMap = OpenUtil.buildParamsMap(entity);
		return this.postJsonByMap(url, paramsMap,locale);
	}
	
	public <T> T postJsonByObj2Obj(String url, Object entity,Class<T> clazz,Locale locale) {
		String json = this.postJsonByObj(url, entity,locale);
		return (T) JSON.parseObject(json, clazz);
	}
	
	public <T> T postJsonByMap2Obj(String url, Map<String, String> params,Class<T> clazz,Locale locale) {
		String json = this.postJsonByMap(url, params,locale);
		return (T) JSON.parseObject(json, clazz);
	}
	
	private String post(String url,Map<String, String> params,String dateType) {
		return this.post(url, params, dateType, Locale.CHINESE);
	}
	
	/**
	 * 发送请求
	 * @param url 请求连接
	 * @param params 请求参数
	 * @return 服务端返回的内容
	 */
	private String post(String url,Map<String, String> params,String dateType,Locale locale) {
		params.put(appIdName, this.appId);
		params.put(timestampName, String.valueOf(System.currentTimeMillis()));
		
		String sign = null;
		Map<String, String> checkSignParam = this.buildCheckSignParam();
		try {
			sign = OpenUtil.buildSign(checkSignParam, this.secret);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ClientException("签名构建失败");
		}
		
		params.put(signName, sign);
		
		try {
			return PostUtil.post(serverCtx + url, params, dateType, locale);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			MsgResult msgResult = new MsgResult();
			msgResult.setCode(ErrorType.SERVICE_CURRENTLY_UNAVAILABLE.value());
			msgResult.setMessage(e.getMessage());
			return JSON.toJSONString(msgResult);
		}
	}
	
	private Map<String,String> buildCheckSignParam() {
		Map<String, String> checkSignParam = new HashMap<String, String>(2);
		checkSignParam.put(appIdName, appId);
		checkSignParam.put(timestampName, String.valueOf(System.currentTimeMillis()));
		return checkSignParam;
	}
	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAppIdName() {
		return appIdName;
	}

	public void setAppIdName(String appIdName) {
		this.appIdName = appIdName;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTimestampName() {
		return timestampName;
	}

	public void setTimestampName(String timestampName) {
		this.timestampName = timestampName;
	}

	public String getServerCtx() {
		return serverCtx;
	}

	public void setServerCtx(String serverCtx) {
		this.serverCtx = serverCtx;
	}

}
