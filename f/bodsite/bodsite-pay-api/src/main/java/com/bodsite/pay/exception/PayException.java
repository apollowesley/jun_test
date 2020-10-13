package com.bodsite.pay.exception;

import com.bodsite.common.exception.BaseException;

/**
 * 支付相关异常 
* @author bod
* @date 2017年1月7日 下午6:41:05 
*
 */
public class PayException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PayException(PAY_EXPECTION ex, Throwable cause) {
		super(ex.getCode(), ex.getMessage(), cause);
	}

	public PayException(PAY_EXPECTION ex) {
		super(ex.getCode(), ex.getMessage());
	}

	public PayException(int code, String message) {
		super(code, message);
	}

	public PayException(String message, Throwable cause) {
		super(BaseException.ERROR_CODE, message, cause);
	}

	public PayException(String message) {
		super(BaseException.ERROR_CODE, message);
	}

	public PayException() {
		super();
	}

	public enum PAY_EXPECTION {
		PAY_PERPAY_ERROR(900000, "预支付失败！"), 
		PAY_RETURN_ERROR(900001, "微信回调验证失败！");
		private PAY_EXPECTION(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
