package com.site.util;

import java.io.Serializable;

public class CardInfo implements Comparable<CardInfo> , Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -57561012312392671L;
	
	private String cardAmount;
	private String cardNo;
	private String cardPin;
	private String cvv2;
	private String webPin;

	public String getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPin() {
		return cardPin;
	}

	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getWebPin() {
		return webPin;
	}

	public void setWebPin(String webPin) {
		this.webPin = webPin;
	}

	@Override
	public int compareTo(CardInfo o) {
		Double double1 = new Double(this.getCardAmount());
		Double double2 = new Double("0");
		if(null!=o){
			double2 = new Double(o.getCardAmount());
		}
		if (double1 == double2) {
			return 0;
		}
		if (double1 > double2) {
			return 1;
		} else {
			return -1;
		}
	}

}
