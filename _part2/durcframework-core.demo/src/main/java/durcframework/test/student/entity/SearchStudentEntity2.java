package durcframework.test.student.entity;

import org.durcframework.core.expression.annotation.LikeDoubleField;
import org.durcframework.core.expression.annotation.LikeLeftField;
import org.durcframework.core.expression.annotation.LikeRightField;

import durcframework.test.common.SearchEasyUI;

/**
 * 负责接收页面查询参数的类 2012-09-21
 */
public class SearchStudentEntity2 extends SearchEasyUI {
	private String schAddress;
	private String schMobile;
	private String name;
	
	@LikeLeftField(column="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@LikeDoubleField(column="address")
	public String getSchAddress() {
		return schAddress;
	}

	public void setSchAddress(String schAddress) {
		this.schAddress = schAddress;
	}

	@LikeRightField(column="mobile")
	public String getSchMobile() {
		return schMobile;
	}

	public void setSchMobile(String schMobile) {
		this.schMobile = schMobile;
	}

}
