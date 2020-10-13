/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.core.processor.project.IProjectProcessor;
import com.autoscript.ui.core.processor.project.ProjectProcessor;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 运行工程Action,生成代码
 * 作者:龙色波
 * 日期:2013-10-13
 */
public class RunProjectAction extends AbstractUIAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2134107923455423461L;

	public RunProjectAction(BatchUI ui) {
		super(ui);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//检查模型
		try {
			//同步
			ui.syncTreeNode(ui.getSelectProjectTreeNode());
			//如果工程尚未保存，则自动保存
			if(ui.getProjectModify()){
				ui.saveProject();
			}
			ui.setProjectRunFlag(true);
			ui.getProjectModel().verify();
			IProjectProcessor processor = new ProjectProcessor(ui.getOutputTextArea());
			processor.build(ui.getProjectModel());
			JOptionPane.showMessageDialog(ui.getFrame(), UIPropertyHelper.getString("system.operation_success"));
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(ui.getFrame(), e1.getMessage());
			logger.error(e1.getMessage(), e1);
		}finally{
			ui.setProjectRunFlag(false);
		}
	}
	  public RunProjectAction(String text, Icon icon,char accelerator, String description) {
		    super(text, icon);

		    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

		    putValue("ShortDescription", description);
		  }
}
