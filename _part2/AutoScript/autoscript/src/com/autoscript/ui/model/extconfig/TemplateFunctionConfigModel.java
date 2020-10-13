/**
 * 
 */
package com.autoscript.ui.model.extconfig;

import java.util.ArrayList;
import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.extconfig.function.IFunctionConfigModel;

/**
 * 模板扩展函数配置模型
 * 作者:龙色波
 * 日期:2013-10-24
 */
public class TemplateFunctionConfigModel implements IExtConfigModel {
	/**
	 * 函数列表
	 */
	private List<IFunctionConfigModel> functionItems = new ArrayList<IFunctionConfigModel>();
	@Override
	public String toString() {
		return UIPropertyHelper.getString("dialog.config.templatefunext");
	}
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.IExtConfigModel#verify()
	 */
	@Override
	public void verify() throws Exception {
		List<String> testNameList = new ArrayList<String>();
		if(functionItems!=null && functionItems.size()>0){
			//循环验证
			for(IFunctionConfigModel config:functionItems){
				config.verify();
				//验证重名
				if(testNameList.contains(config.getName())){
					throw new Exception(UIPropertyHelper.getString("exception.function_name_is_exists", config.getName()));
				}
				testNameList.add(config.getName());
			}
		}
	}
	public List<IFunctionConfigModel> getFunctionItems() {
		return functionItems;
	}
	public void setFunctionItems(List<IFunctionConfigModel> functionItems) {
		this.functionItems = functionItems;
	}
	public String[][] toArray() {
		String data[][] = new String[functionItems.size()][3];
		IFunctionConfigModel model;
		for(int i=0;i<functionItems.size();i++){
			model = functionItems.get(i);
			data[i][0] = model.getName();
			data[i][1] = model.getFunctionClass();
			data[i][2] = model.getDescribe();
		}
		return data;
	}

}
