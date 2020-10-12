package durcframework.test.student.entity;

import org.durcframework.core.SearchEntity;

/**
 * 参数类的默认实现,后续定义的查询参数可以继承该类
 * @author hc.tang
 *
 */
public class OpenSearchEntity extends SearchEntity {
	private String appId;
	private String sign;
	private long timestamp;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
