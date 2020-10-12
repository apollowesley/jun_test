package com.evil.util;

import java.util.List;
import java.util.Map;

import com.evil.pojo.system.TypeDictionary;
import com.opensymphony.xwork2.ActionContext;

public class DictionaryUtil {

	public static void DictionaryWrite(List<TypeDictionary> types) {
		Map<String, Object> context = ActionContext.getContext().getApplication();
		context.put("paperTypes", types);
	}

}
