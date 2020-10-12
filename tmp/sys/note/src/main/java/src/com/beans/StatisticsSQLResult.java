package com.beans;

import java.math.BigDecimal;
import java.util.Date;

public class StatisticsSQLResult {
	private Date day;
	private BigDecimal amount;

	public StatisticsSQLResult() {
	}

	public StatisticsSQLResult(Date day, BigDecimal amount) {
		this.day = day;
		this.amount = amount;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
