package com.managementsystem.notification.transaction;

import org.aspectj.lang.ProceedingJoinPoint;

public interface TransactionSchema {
	/**
	 * 操作数据库时事务的前置通知
	 */
	void startTransaction();
	/**
	 * 操作数据库时事务的后置通知
	 */
	void commitTransaction();
	/**
	 * 操作数据库时事务的环绕通知
	 */
	void around(ProceedingJoinPoint joinPoint) throws Throwable;
	

}
