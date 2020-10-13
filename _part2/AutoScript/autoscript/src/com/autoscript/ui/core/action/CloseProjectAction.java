/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;

/**
 * 关闭工程Action
 * 作者:龙色波
 * 日期:2013-10-15
 */
public class CloseProjectAction extends AbstractUIAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3950129016206687182L;

	public CloseProjectAction(BatchUI ui) {
		super(ui);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ui.closeProject();
	}
	  public CloseProjectAction(String text, Icon icon, char accelerator, String description) {
		    super(text, icon);

		    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

		    putValue("ShortDescription", description);
		  }
}
