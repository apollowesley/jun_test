package priv.mdc.index.dumper.model;

import java.text.MessageFormat;

public class IndexDocReq {

	private String index;
	private String type;
	private String id;
	private String action;
	private String reqBody;

	public IndexDocReq() {
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReqBody() {
		return reqBody;
	}

	public void setReqBody(String reqBody) {
		this.reqBody = reqBody;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 返回单个请求的URI
	 * 
	 * @return
	 */
	public String getSingleReqUri() {
		return "/" + index + "/" + type + "/" + id;
	}

	/**
	 * 返回批量请求的URI
	 * @return
	 */
	public static String getBatchReqUri() {
		return "/_bulk";
	}
	
	public String getSingleReqMethod() {
		if("index".equals(action)){
			return "POST";
		}else if("update".equals(action)){
			return "POST";
		}else if("delete".equals(action)){
			return "DELETE";
		}
		return "POST";
	}
	
	/**
	 * 返回批量请求的请求体 
	 * 
	 * @return
	 */
	public String getBatachReqBody() {
		String str = MessageFormat
				.format("'{' \"{0}\": '{' \"_index\": \"{1}\", \"_type\": \"{2}\", \"_id\": \"{3}\" '}}'\n",
						action, index, type, id);
		if("delete".equals(action)){
			return str;
		}else if("index".equals(action)){
			return str + reqBody + "\n";
		}else if("update".equals(action)){
			return str + "{\"doc\" : " + reqBody + "}\n";
		}
		return null;
	}

	@Override
	public String toString() {
		return "IndexDocReq [index=" + index + ", type=" + type + ", id=" + id
				+ ", action=" + action + ", reqBody=" + reqBody + "]";
	}

}
