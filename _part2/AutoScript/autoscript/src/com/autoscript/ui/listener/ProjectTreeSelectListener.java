/**
 * 
 */
package com.autoscript.ui.listener;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.autoscript.ui.core.BatchUI;

/**
 * 工程树选择侦听器
 * 作者:龙色波
 * 日期:2013-10-8
 */
public class ProjectTreeSelectListener implements TreeSelectionListener {

	private BatchUI ui;
	public ProjectTreeSelectListener(BatchUI ui){
		this.ui = ui;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath leadPath = e.getNewLeadSelectionPath( );
	    if (leadPath != null) {
	      DefaultMutableTreeNode leadSelection = (DefaultMutableTreeNode)leadPath.getLastPathComponent( );
	      ui.setSelectProjectTreeNode(leadSelection);
	    }


	}

}
