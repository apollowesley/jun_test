package com.evil.pojo.system;

import com.evil.pojo.BaseEntity;


public class Right extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String rightName="";;
	private String rightDesc;
	private String rightUrl;
	private int rightPos;  //Ȩ��λ  ���������� 
	private long rightCode; //Ȩ���� ֵΪ 1l<<n
	
	private boolean common;//�Ƿ��ǹ�����Դ
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}
	
	
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	
	public String getRightDesc() {
		return rightDesc;
	}
	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}
	public int getRightPos() {
		return rightPos;
	}
	public void setRightPos(int rightPos) {
		this.rightPos = rightPos;
	}
	public long getRightCode() {
		return rightCode;
	}
	public void setRightCode(long rightCode) {
		this.rightCode = rightCode;
	}
	public String getRightUrl() {
		return rightUrl;
	}
	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	public boolean isCommon() {
		return common;
	}
	public void setCommon(boolean common) {
		this.common = common;
	}
	

}
