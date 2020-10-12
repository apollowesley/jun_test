package org.nature4j.framework.bean;

public class ValidatorFail {
	private Object result;
	private ValidatorFail(){}
	
	/**
	 * 返回页面或数据
	 * @param result 产生错误后返回值，类似Ctrl的return值
	 */
	public static ValidatorFail backTo(Object result){
		ValidatorFail vf = new ValidatorFail();
		vf.setResult(result);
		return vf;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ValidatorFail [result=" + result + "]";
	}
	
}
