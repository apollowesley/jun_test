package com.zhoux.freemarker.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class codeBaseCon implements CodeBase {
	 
	private static final Logger logger =LoggerFactory.getLogger(codeBaseCon.class);
	
	public abstract boolean createJavaBean(String tableName,String codePath);

	@Override
	public boolean createCodeFile(String tableName, String fileType,String codePath) {
		if("javaBean".equals(fileType)){
			logger.debug("create javabean file!");
			return createJavaBean(tableName, codePath);
		}
		return false;
	}

}
