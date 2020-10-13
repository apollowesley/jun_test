/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.model.projecttree.ITreeModel;
import com.autoscript.ui.model.projecttree.TemplateModel;

/**
 * 删除模板Action
 * 作者:龙色波
 * 日期:2013-11-4
 */
public class DelTemplateAction extends AbstractUIAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8338327844536513517L;

	public DelTemplateAction(BatchUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	public DelTemplateAction(String text, Icon icon, String description, char accelerator) {
    super(text, icon);

    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

    putValue("ShortDescription", description);
  }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(ui.getSelectProjectTreeNode()!=null ){
			ITreeModel model = (ITreeModel) ui.getSelectProjectTreeNode().getUserObject();
			if(model.getType()== ITreeModel.TEMPLATE_TYPE){
				TemplateModel templateModel = (TemplateModel) model;
				String msg;
				msg ="是否删除模板:"+templateModel.getTemplateName()+"?";
				if(JOptionPane.showConfirmDialog(ui.getFrame(), msg,"删除模板",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					ui.delTemplateNode(ui.getSelectProjectTreeNode());
					
				}
			}
		}

	}

}
