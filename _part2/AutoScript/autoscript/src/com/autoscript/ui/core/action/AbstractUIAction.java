/**
 * 
 */
package com.autoscript.ui.core.action;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.logger.UILogger;

/**
 * 抽象UI的Action
 * 作者:龙色波
 * 日期:2013-10-7
 */
public abstract class AbstractUIAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7855048533610484960L;
	protected BatchUI ui;
	protected static Logger logger = UILogger.getLogger("UIAction");
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public AbstractUIAction(BatchUI ui){
		this.ui = ui;
	}
	public AbstractUIAction(String text, Icon icon) {
		super(text,icon);
	}
	public BatchUI getUi() {
		return ui;
	}
	public void setUi(BatchUI ui) {
		this.ui = ui;
	}


}
