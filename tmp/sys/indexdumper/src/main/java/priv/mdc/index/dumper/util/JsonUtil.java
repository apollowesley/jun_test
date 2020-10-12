package priv.mdc.index.dumper.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	private static ObjectMapper mapper;
	private static Object lock = new Object();

	private static ObjectMapper getMapper() {
		if (mapper == null) {
			synchronized(lock){
				if (mapper == null) {
					mapper = new ObjectMapper();
					mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
					mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
					mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true); 
					mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
				}
			}
		}
		return mapper;
	}
	
	/**
	 * 把对象序列化为json字符串
	 * @param o
	 * @return
	 */
	public static String jsonEncode(Object o) {
		if(o==null){
			return null;
		}
		String json = null;
		try {
			json = getMapper().writeValueAsString(o);
		} catch (Exception e) {
			logger.error("jsonEncode:" + o, e);
			json = null;
		}
		return json;
	}
	
	/**
	 * 把json字符串转为list
	 * @param json
	 * @param t
	 * @return
	 */
	public static List json2list(String json) {
		try {
			return getMapper().readValue(json, List.class);
		} catch (Exception e) {
			logger.error("json2list : " + json, e);
			return new ArrayList();
		}
	}	
	
	/**
	 * 把json字符串转为map
	 * @param json
	 * @return
	 */
	public static Map<String, Object> json2map(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (json == null || "".equals(json)) {
			return map;
		}
		try {
			return getMapper().readValue(json, Map.class);
		} catch (Exception e) {
			logger.error("json2map : " + json, e);
			return new HashMap<String,Object>();
		}
	}
	
	
	public static Object json2Obj(String json){
		if (json == null || "".equals(json)) {
			return new Object();
		}
		try {
			return getMapper().readValue(json, Object.class);
		} catch (Exception e) {
			logger.error("json2map : " + json, e);
			return new Object();
		}
	}
	
}
