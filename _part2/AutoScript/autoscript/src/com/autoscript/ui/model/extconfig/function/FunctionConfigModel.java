/**
 * 
 */
package com.autoscript.ui.model.extconfig.function;

import org.apache.log4j.Logger;

import com.autoscript.ui.core.UIConstants;
import com.autoscript.ui.helper.ClassHelper;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.logger.UILogger;


/**
 * FreeMark函数配置
 * 作者:龙色波
 * 日期:2013-10-24
 */
public class FunctionConfigModel implements IFunctionConfigModel {
	protected Logger logger = UILogger.getLogger(FunctionConfigModel.class);
	/**
	 * 方法描述
	 */
	private String describe;
	/**
	 * 方法对应的类
	 */
	private String functionClass;
	/**
	 * 函数名
	 */
	private String name;

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.function.IFunctionConfig#getDescribe()
	 */
	@Override
	public String getDescribe() {
		return this.describe;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.function.IFunctionConfig#getFunctionClass()
	 */
	@Override
	public String getFunctionClass() {
		return this.functionClass;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.function.IFunctionConfig#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.function.IFunctionConfig#setDescribe(java.lang.String)
	 */
	@Override
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.function.IFunctionConfig#setFunctionClass(java.lang.String)
	 */
	@Override
	public void setFunctionClass(String className) {
		this.functionClass = className;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.function.IFunctionConfig#setName(java.lang.String)
	 */
	@Override
	public void setName(String funName) {
		this.name = funName;
	}

	@Override
	public void verify() throws Exception {
		//函数名称不能为空
		if(StringHelper.isEmpty(name)){
			throw new Exception(UIPropertyHelper.getString("exception.function_name_isempty"));
		}
		//不能与FreeMark关键字冲突
		if(UIConstants.ROOT_NODE.equals(name)){
			throw new Exception(UIPropertyHelper.getString("exception.reservekey.conflict",UIConstants.ROOT_NODE ));
		}
		//实现类不能为空
		if(StringHelper.isEmpty(functionClass)){
			throw new Exception(UIPropertyHelper.getString("exception.function_class_isempty"));
		}
		//类必须能装入
		Class<?> implClass=null;
		try{
			implClass = Class.forName(functionClass);
		}catch(LinkageError e){
			logger.error(e.getMessage(), e);
			throw new Exception(UIPropertyHelper.getString("exception.loadClassFail",functionClass, e.getMessage()));
		}catch(ClassNotFoundException  e1){
			logger.error(e1.getMessage(), e1);
			throw new Exception(UIPropertyHelper.getString("exception.loadClassFail",functionClass, e1.getMessage()));
		}
		//实现类必须实现TemplateMethodModel
		if(implClass!=null){
			String interfaceClassName="freemarker.template.TemplateMethodModel";			
			if(!ClassHelper.isImplInterface(implClass, interfaceClassName)){
				throw new Exception(UIPropertyHelper.getString("exception.implClassMustImplInterface", functionClass,interfaceClassName));
			}
		}
		 
		
	}

}
