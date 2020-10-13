package com.bodsite.search.exception;

import com.bodsite.common.exception.BaseException;

public class SearchException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchException(SEARCH_EXPECTION ex,Throwable cause){
		super(ex.getCode(),ex.getMessage(),cause);
	}
	
	public SearchException(SEARCH_EXPECTION ex){
		super(ex.getCode(),ex.getMessage());
	}
	
	public SearchException(int code,String message){
		super(code,message);
	}
	
	public SearchException(String message,Throwable cause){
		super(BaseException.ERROR_CODE,message,cause);
	}
	
	public SearchException(String message){
		super(BaseException.ERROR_CODE,message);
	}
	
	public SearchException(){
		super();
	}
	
	public enum SEARCH_EXPECTION{
		SOLR_CONNECT_ADDRESS_ERROR(700001,"solr连接地址错误！"),
		SOLR_ADD_ERROR(700002,"solr新增失败！"),
		SOLR_DEL_ERROR(700003,"solr删除失败！"),
		SOLR_QUERY_ERROR(700004,"solr查询失败！"),

		SOLR_OPTIMIZE_ERROR(700006,"solr优化失败");
		private SEARCH_EXPECTION(int code, String message) {
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
