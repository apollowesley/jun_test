/**
 * 
 */
package com.autoscript.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

/**
 * 快捷键侦听器
 * 作者:龙色波
 * 日期:2013-11-11
 */
public class KeyboardListener implements ActionListener {
	private Action actionClass;
	public KeyboardListener(Action actionClass){
		this.actionClass = actionClass;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(actionClass!=null){
			actionClass.actionPerformed(e);
		}
	}

}
