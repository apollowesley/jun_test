package org.nature4j.framework.restful;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nature4j.framework.bean.CtrlBean;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.helper.CtrlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestfulTransfer {
	private static Logger LOGGER = LoggerFactory.getLogger(RestfulTransfer.class);
	private static List<RestBean> restBeans = new ArrayList<RestBean>();
	private RestfulTransfer(){}
	public static void initRestBeans(){
		Map<String, CtrlBean> ctrlMap = CtrlHelper.getCtrlMap();
		Set<String> keySet = ctrlMap.keySet();
		for (String targetKey : keySet) {
			if (targetKey.matches(".*/\\{.*\\}.*")) {
				Pattern namesPattern = Pattern.compile("\\{([\\w\\.\\s\\%]+)\\}");
				Matcher namesMatcher = namesPattern.matcher(targetKey);
				List<String> names = new ArrayList<String>();
				while(namesMatcher.find()){
					String name = namesMatcher.group(1);
					names.add(name);
				}
				String regexKey = "^"+targetKey.replaceAll("/\\{[\\w\\.\\-\\s]+\\}", "/([\\\\w\\\\.\\\\s\\\\-%]+)")+"$";
				restBeans.add(new RestBean(targetKey, regexKey, names));
			}
		}
	}
	
	public static String restfulTransfer(String targetKey, NatureMap requestParams){
		try {
			for (RestBean restBean : restBeans) {
				if (targetKey.matches(restBean.getRegexKey())) {
					Pattern pattern = Pattern.compile(restBean.getRegexKey());
					Matcher matcher = pattern.matcher(targetKey);
					while(matcher.find()){
						int groupCount = matcher.groupCount();
						for (int j = 0; j < groupCount; j++) {
							String value = matcher.group(j+1);
							requestParams.put(restBean.getNames().get(j), URLDecoder.decode(value,"UTF-8"));
						}
					}
					return restBean.getTargetKey();
				}
			}
		} catch (Exception e) {
			LOGGER.error("restful url is not right");
			throw new RuntimeException(e);
		}
		return targetKey;
	}
	
	
	public static List<RestBean> getRestBeans() {
		return restBeans;
	}


	public static void clear() {
		restBeans.clear();
	}
	
	
	
}
