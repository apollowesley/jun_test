/**
 * 
 */
package com.autoscript.ui.config;

import java.io.FileNotFoundException;

import com.autoscript.ui.core.UIConstants;
import com.autoscript.ui.helper.FileCtrlUtils;
import com.autoscript.ui.helper.SerialHelper;

/**
 * 配置类代理
 * 作者:龙色波
 * 日期:2013-10-13
 */
public class AutoScriptConfigProxy {
	private static AutoScriptConfigProxy proxy;
	private static AutoScriptConfig config;
	private AutoScriptConfigProxy(){
		
	}
	public synchronized static AutoScriptConfigProxy getInstance(){
		if(proxy==null){
			proxy = new AutoScriptConfigProxy();
		}
		return proxy;
	}
	/**
	 * 从xml读取配置
	 * @return
	 * @throws FileNotFoundException
	 */
	public  AutoScriptConfig read() throws FileNotFoundException{
		String xmlFile;
		xmlFile = UIConstants.AUTOSCRIPT_CONFFILE;
		if(config==null){
			if(FileCtrlUtils.isExists(xmlFile)){
				config = (AutoScriptConfig) SerialHelper.readFromXml(xmlFile);
			}else{
				config = new AutoScriptConfig();
			}
		}
		return config;
	}
	/**
	 * 配置复位，方便重读
	 */
	public void resetConfig(){
		config = null;
	}
	public void save() throws FileNotFoundException{
		String xmlFile;
		xmlFile = UIConstants.AUTOSCRIPT_CONFFILE;
		if(config!=null){
			SerialHelper.saveToXml(xmlFile, config);
		}
	}
}
