/**
 * 
 */
package com.autoscript.ui.model.extconfig;

import java.util.ArrayList;
import java.util.List;

import com.autoscript.ui.core.UIConstants;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.extconfig.kb.IKBItemModel;

/**
 * 知识库扩展模型
 * 作者:龙色波
 * 日期:2013-11-12
 */
public class KBConfigModel implements IExtConfigModel {
	//知识库配置项列表
	List<IKBItemModel> kbItems = new ArrayList<IKBItemModel>();
	@Override
	public  String toString() {
		return UIPropertyHelper.getString("dialog.config.kbext");
	}
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.IExtConfigModel#verify()
	 */
	@Override
	public void verify() throws Exception {
		if(kbItems!=null){
			for(IKBItemModel model:kbItems){
				model.verify();
			}
		}

	}
	public List<IKBItemModel> getKbItems() {
		return kbItems;
	}
	public void setKbItems(List<IKBItemModel> kbItems) {
		this.kbItems = kbItems;
	}
	/**
	 * 将知识库项列表转换为两级数组
	 * @return
	 */
	public String[][] toArray() {
		String data[][] = new String[kbItems.size()][4];
		IKBItemModel model;
		for(int i=0;i<kbItems.size();i++){
			model = kbItems.get(i);
			data[i][0] = model.getType();
			data[i][1] = model.getKeyFun();
			data[i][2] = model.getSyntax();
			data[i][3] = model.getDescribe();
		}
		return data;
	}
	/**
	 * 获取所有类型（剔除重复的)
	 * @return
	 */
	public List<String> getAllTypes() {
		List<String> types = new ArrayList<String>();
		for(IKBItemModel model:kbItems){
			if(!types.contains(model.getType())){
				types.add(model.getType());
			}
		}
		return types;
	}
	/**
	 * 获取所有关键字或函数
	 * @param type
	 * @return
	 */
	public List<String> getAllKeyFunsByType(String type) {
		List<String> keyFuns = new ArrayList<String>();
		for(IKBItemModel model:kbItems){
			if(!type.equals(UIConstants.ALL_KEY)){
				if(type.equals(model.getType())){
					keyFuns.add(model.getKeyFun());
				}
			}else{
				keyFuns.add(model.getKeyFun());
			}
		}
		return keyFuns;
	}
	/**
	 * 获取所有关键字或函数
	 * @param type
	 * @return
	 */
	public List<IKBItemModel> getAllModelByType(String type) {
		List<IKBItemModel> keyFuns = new ArrayList<IKBItemModel>();
		for(IKBItemModel model:kbItems){
			if(!type.equals(UIConstants.ALL_KEY)){
				if(type.equals(model.getType())){
					keyFuns.add(model);
				}
			}else{
				keyFuns.add(model);
			}
		}
		return keyFuns;
	}
	/**
	 * 根据类型和关键字函数搜索模型
	 * @param type
	 * @param keyFun
	 * @return
	 */
	public IKBItemModel getKBItem(String type, String keyFun) {
		if(!StringHelper.isEmpty(type) && !StringHelper.isEmpty(keyFun)){
			for(IKBItemModel model:kbItems){
				if(!type.equals(UIConstants.ALL_KEY)){
					if(type.equals(model.getType()) && keyFun.equals(model.getKeyFun())){
						return model;
					}
				}else{
					if(keyFun.equals(model.getKeyFun())){
						return model;
					}
				}
			}
		}
		return null;
	}
}
