/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.model.projecttree.ITreeModel;
import com.autoscript.ui.model.projecttree.TemplateModel;

/**
 * 启用模板
 * 作者:龙色波
 * 日期:2014-4-28
 */
public class EnableTemplateAction extends AbstractUIAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4396964198606556830L;
	public EnableTemplateAction(BatchUI ui) {
		super(ui);
	}
	public EnableTemplateAction(String text, Icon icon, String description, char accelerator) {
	    super(text, icon);

	    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

	    putValue("ShortDescription", description);
	  }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(ui.getSelectProjectTreeNode()!=null ){
			ITreeModel model = (ITreeModel) ui.getSelectProjectTreeNode().getUserObject();
			if(model.getType()== ITreeModel.TEMPLATE_TYPE){
				TemplateModel templateModel = (TemplateModel) model;
				templateModel.setEnable(true);
				ui.setProjectModify(true);
				ui.getTree().updateUI();
			//如果是根节点，则启用所有模板
			}else if(model.getType()==ITreeModel.ROOT_TYPE){
				for(TemplateModel templateModel:ui.getProjectModel().getTemplateModels()){
					templateModel.setEnable(true);
				}
				ui.setProjectModify(true);
				ui.getTree().updateUI();
			}
		}

	}

}
