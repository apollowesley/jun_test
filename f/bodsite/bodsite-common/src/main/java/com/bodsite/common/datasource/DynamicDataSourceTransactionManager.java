package com.bodsite.common.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 
 * @Description:根据事务这是数据源(必须有事务才能进入)
 * @author bod
 * @date
 *
 */
public class  DynamicDataSourceTransactionManager extends DataSourceTransactionManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		boolean readOnly = definition.isReadOnly();
		if(readOnly){//只读
			DataSourceHandler.setSlave();
		}else{//读写
			DataSourceHandler.setMaster();
		}
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		DataSourceHandler.DataSoruceClean();
	}
	
}
