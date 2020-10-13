package com.managementsystem.notification.transaction;

import org.aspectj.lang.ProceedingJoinPoint;

public class TransactionSchemaImpl  implements TransactionSchema{

	@Override
	public void startTransaction() {
		System.out.println("begin transaction ");
		
	}

	@Override
	public void commitTransaction() {
		System.out.println("commit transaction ");
		
	}

    //环绕通知
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("begin transaction");
        
        joinPoint.proceed();
        
        System.out.println("commit transaction");
    }
}
