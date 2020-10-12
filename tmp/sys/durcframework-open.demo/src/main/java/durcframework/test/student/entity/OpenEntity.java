package durcframework.test.student.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class OpenEntity {
	@XStreamOmitField // xml序列化忽略此字段,下同
	private String appId;
	@XStreamOmitField
	private String sign;
	@XStreamOmitField
	private long timestamp;

	@JSONField(serialize=false) // json序列化忽略此字段,下同
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@JSONField(serialize=false)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@JSONField(serialize=false)
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
