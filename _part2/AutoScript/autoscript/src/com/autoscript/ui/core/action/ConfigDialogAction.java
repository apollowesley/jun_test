/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.core.component.ConfigDialog;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 配置对话框Action
 * 作者:龙色波
 * 日期:2013-10-13
 */
public class ConfigDialogAction extends AbstractUIAction {

	public ConfigDialogAction(BatchUI ui) {
		super(ui);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4742156857337702294L;

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ConfigDialog dlg = new ConfigDialog(ui.getFrame(),UIPropertyHelper.getString("dialog.configdialog.title"),true);
		dlg.setVisible(true);
	}

}
