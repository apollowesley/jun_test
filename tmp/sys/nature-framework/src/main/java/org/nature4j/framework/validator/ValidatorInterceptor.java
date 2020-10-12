package org.nature4j.framework.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.nature4j.framework.bean.ValidatorFail;
import org.nature4j.framework.interceptor.Invocation;
import org.nature4j.framework.interceptor.NatureInterceptor;

public class ValidatorInterceptor implements NatureInterceptor {

	public void intercept(Invocation invocation) {
		List<NatureValidator> validators = invocation.getValidators();
		Map<String, String> errorMap = new HashMap<String, String>();
		ValidatorFail validate = null;
		for (NatureValidator validator : validators) {
			validate = validator.validate(invocation.getRequestParams(), errorMap);
		}
		if (errorMap.isEmpty()) {
			invocation.invoke();
		}else{
			Set<Entry<String, String>> entrySet = errorMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				invocation.getRequest().setAttribute(entry.getKey(), entry.getValue());
				entrySet=null;
			}
			invocation.setReturnValue(validate);
		}
	}

	public int level() {
		return 1020;
	}

}
