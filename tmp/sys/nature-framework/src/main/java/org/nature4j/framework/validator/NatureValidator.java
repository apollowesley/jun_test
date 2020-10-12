package org.nature4j.framework.validator;

import java.util.Map;

import org.nature4j.framework.bean.ValidatorFail;
import org.nature4j.framework.core.NatureMap;

public interface NatureValidator {
	
	/**
	 * 参数格式验证
	 * @param params 所有参数
	 * @param errorMap 错误提示信息
	 * @return 有错误时返回页面或内容（类似Ctrl中的方法返回）
	 */
	ValidatorFail validate(NatureMap params , Map<String, String> errorMap);
	
}
