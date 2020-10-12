package org.durcframework.util;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.durcframework.entity.ValidateAware;
import org.durcframework.entity.ValidateHolder;

/**
 * 验证工具类 JSR303
 * 
 * @author hc.tang 2014年6月5日
 * 
 */
public class ValidateUtil {

	private static Validator validator;
	private static final ValidateHolder SUCCESS_HOLDER = new ValidateHolder();

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		SUCCESS_HOLDER.setSuccess(true);
		SUCCESS_HOLDER.setConstraintViolations(Collections
				.<ConstraintViolation<Object>> emptySet());
	}

	public static boolean isNeedValidate(Object obj) {
		return obj instanceof ValidateAware;
	}

	/**
	 * 验证
	 * @param obj 该类必须实现ValidateAware接口
	 * @return
	 */
	public static ValidateHolder validate(Object obj) {

		if (isNeedValidate(obj)) {

			ValidateHolder holder = new ValidateHolder();

			Set<ConstraintViolation<Object>> set = validator.validate(obj);

			holder.setConstraintViolations(set);
			holder.setSuccess(set.size() == 0);

			return holder;

		} else {
			return SUCCESS_HOLDER;
		}
	}

}
