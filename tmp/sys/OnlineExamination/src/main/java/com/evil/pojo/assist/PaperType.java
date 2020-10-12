package com.evil.pojo.assist;

public class PaperType {
	private String paperTypeNmae; // 试卷类型的名字
	private int PaperTypeId; // 试卷类型的编号
	private long allNum; // 该类试卷的总数量
	private long finshNum; // 已经完成的数量

	public String getPaperTypeNmae() {
		return paperTypeNmae;
	}

	public void setPaperTypeNmae(String paperTypeNmae) {
		this.paperTypeNmae = paperTypeNmae;
	}

	public int getPaperTypeId() {
		return PaperTypeId;
	}

	public void setPaperTypeId(int paperTypeId) {
		PaperTypeId = paperTypeId;
	}

	public long getAllNum() {
		return allNum;
	}

	public void setAllNum(long allNum) {
		this.allNum = allNum;
	}

	public long getFinshNum() {
		return finshNum;
	}

	public void setFinshNum(long finshNum) {
		this.finshNum = finshNum;
	}

}
