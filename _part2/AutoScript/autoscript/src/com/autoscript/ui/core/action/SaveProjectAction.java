/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;

/**
 * 保存工程Action
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class SaveProjectAction extends AbstractUIAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SaveProjectAction(BatchUI ui){
		super(ui);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ui.saveProject();
	}
	public SaveProjectAction(String text, Icon icon,char accelerator,String description) {
		    super(text, icon);

		    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

		    putValue("ShortDescription", description);
		  }
}
