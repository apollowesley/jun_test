/**
 * 
 */
package com.autoscript.ui.listener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.autoscript.ui.core.component.ConfigDialog;

/**
 * 扩展功能列表选择事件侦听器
 * 作者:龙色波
 * 日期:2013-10-16
 */
public class ExtConfigModelListListener implements ListSelectionListener {
	private ConfigDialog dialog;
	private int selectIndex=-1;
	public ExtConfigModelListListener(ConfigDialog dialog){
		this.dialog = dialog;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()){
			dialog.refreshExtConfigDisp(selectIndex,e.getFirstIndex());
			selectIndex = e.getFirstIndex();
			//清空选择
			dialog.getExtConfigModelList().clearSelection();
		}
		

	}
	public int getSelectIndex() {
		return selectIndex;
	}

}
