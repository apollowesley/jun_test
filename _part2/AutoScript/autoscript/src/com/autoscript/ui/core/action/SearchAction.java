/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.core.component.FindReplaceDialog;

/**
 * 查询textarea编辑区Action
 * 作者:龙色波
 * 日期:2013-11-6
 */
public class SearchAction extends AbstractUIAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8811516157928490303L;
	public SearchAction(BatchUI ui) {
		super(ui);
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		FindReplaceDialog dlg;
		dlg = new FindReplaceDialog(ui.getFrame(), ui.getTextArea());
		dlg.setVisible(true);

	}

}
