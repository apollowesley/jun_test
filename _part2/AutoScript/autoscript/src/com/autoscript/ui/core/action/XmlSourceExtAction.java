/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.autoscript.extinterface.xmldata.IMakeXmlData;
import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.model.projecttree.ITreeModel;

/**
 * xml源数据扩展菜单
 * 作者:龙色波
 * 日期:2013-10-23
 */
public class XmlSourceExtAction extends AbstractUIAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 335654346851309782L;
	/**
	 * 构造xml源数据接口
	 */
	private IMakeXmlData makeXmlData;
	public XmlSourceExtAction(String text, Icon icon) {
		super(text,icon);
	}
	public XmlSourceExtAction(BatchUI ui) {
		super(ui);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        String xml=null;
		try {
			xml = makeXmlData.makeXml(ui.getFrame());
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage(), e1);
			JOptionPane.showMessageDialog(ui, e1.getMessage());
			return;
		}
		//如果产生了xml
		if(!StringHelper.isEmpty(xml)){
			//且目前选择的节点类型为xml源数据
			if(ui.getSelectProjectTreeNode()!=null ){
				ITreeModel treeModel;
				treeModel = (ITreeModel) ui.getSelectProjectTreeNode().getUserObject();
				if(ITreeModel.XML_DATA_TYPE == treeModel.getType()){
					ui.setEditorContent(xml);
				}
			}
		}
	}

	public IMakeXmlData getMakeXmlData() {
		return makeXmlData;
	}

	public void setMakeXmlData(IMakeXmlData makeXmlData) {
		this.makeXmlData = makeXmlData;
	}

}
