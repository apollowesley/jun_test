package com.foo.common.base.pojo;

import java.util.Date;

import org.apache.commons.lang3.SystemUtils;

/**
 * 描述打包信息的pojo，最后会转换为json供公网访问。
 * 
 * @author Steve
 *
 */
public class CommonPatchRelease {
	// 当前到包截至ACC
	private String commitAcc = "";
	// 本次打包时间
	private Date patchDateTime = new Date();
	// 打包用户名
	private String patchUserName = SystemUtils.USER_NAME;

	// 打包内容，todoo，安全因素考虑，暂时不要打包详细内容

	public String getCommitAcc() {
		return commitAcc;
	}

	public void setCommitAcc(String commitAcc) {
		this.commitAcc = commitAcc;
	}

	public Date getPatchDateTime() {
		return patchDateTime;
	}

	public String getPatchUserName() {
		return patchUserName;
	}

}
