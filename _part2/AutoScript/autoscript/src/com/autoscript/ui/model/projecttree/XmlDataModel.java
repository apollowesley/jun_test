/**
 * 
 */
package com.autoscript.ui.model.projecttree;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * Xml数据模型
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class XmlDataModel implements ITreeModel {
	/**
	 * xml内容
	 */
    private String xml;
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.projecttree.ITreeModel#getType()
	 */
	@Override
	public int getType() {
		return XML_DATA_TYPE;
	}
    public String toString(){
    	return UIPropertyHelper.getString("tree.xmldata.label");
    }
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	@Override
	public void verify() throws Exception {
		if(StringHelper.isEmpty(xml)){
			throw new Exception(UIPropertyHelper.getString("exception.xml_data_is_empty"));
		}
		
	}
}
