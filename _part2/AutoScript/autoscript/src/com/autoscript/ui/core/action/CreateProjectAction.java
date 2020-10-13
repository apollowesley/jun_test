/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 创建工程Action
 * 作者:龙色波
 * 日期:2013-10-7
 */
public class CreateProjectAction extends AbstractUIAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -68150091750154314L;
	
	public CreateProjectAction(BatchUI ui){
		super(ui);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(ui.getFrame(), UIPropertyHelper.getString("system.createproject"), UIPropertyHelper.getString("system.info"), 0) == 0) {
			ui.createProjectTree();
		}

	}
    public CreateProjectAction(String text, Icon icon,char accelerator, String description) {
		    super(text, icon);

		    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

		    putValue("ShortDescription", description);
		  }
}
