/**
 * 
 */
package com.autoscript.ui.listener;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.autoscript.ui.core.action.AddTemplateAction;
import com.autoscript.ui.core.action.CreateProjectAction;
import com.autoscript.ui.core.action.DelTemplateAction;
import com.autoscript.ui.core.action.DisableTemplateAction;
import com.autoscript.ui.core.action.DoNothingAction;
import com.autoscript.ui.core.action.EnableTemplateAction;
import com.autoscript.ui.core.action.XmlSourceExtAction;
import com.autoscript.ui.core.component.UIPopupMenu;
import com.autoscript.ui.model.projecttree.ITreeModel;

/**
 * 右键鼠标侦听器
 * 作者:龙色波
 * 日期:2013-10-8
 */
public class ProjectTreeMousePopupListener extends MouseAdapter {
	 private UIPopupMenu popup;
	 private Component parent;
	 public ProjectTreeMousePopupListener(Component parent, UIPopupMenu popup){
		 this.parent = parent;
		 this.popup = popup;
	 }
	 public void mousePressed(MouseEvent e) { checkPopup(e); }
     public void mouseClicked(MouseEvent e) { checkPopup(e); }
     public void mouseReleased(MouseEvent e) { checkPopup(e); }

     private void checkPopup(MouseEvent e) {
    	 JTree tree = (JTree) parent;
         if (e.isPopupTrigger( )) {
        	 TreePath leadPath = tree.getSelectionPath();
         
     	    if (leadPath != null) {
     	      DefaultMutableTreeNode selectTreeNode = (DefaultMutableTreeNode)leadPath.getLastPathComponent( );
     	      enableMenu(selectTreeNode);
              popup.show(parent, e.getX( ), e.getY( ));
     	    }
         }
     }
    /**
     * 根据选择的树节点，禁止/激活菜单项
     * @param selectTreeNode
     */
	private void enableMenu(DefaultMutableTreeNode selectTreeNode) {
		if(selectTreeNode!=null){
			if(selectTreeNode.getUserObject() instanceof ITreeModel){
				ITreeModel treeModel = (ITreeModel) selectTreeNode.getUserObject();
				//xml源数据
				switch (treeModel.getType()){
				case ITreeModel.XML_DATA_TYPE:
					popup.setEnable(new Class[]{XmlSourceExtAction.class});
					break;
				//根节点
				case ITreeModel.ROOT_TYPE:
					popup.setEnable(new Class[]{AddTemplateAction.class,DelTemplateAction.class,EnableTemplateAction.class,DisableTemplateAction.class});
					break;
				//模板节点
				case ITreeModel.TEMPLATE_TYPE:
					popup.setEnable(new Class[]{DelTemplateAction.class,EnableTemplateAction.class,DisableTemplateAction.class});
					break;
				//其他节点禁止菜单
				default:
					popup.setEnable(new Class[]{DoNothingAction.class});
					break;
				}
				
			}
		}else{
			popup.setEnable(new Class[]{CreateProjectAction.class});
		}
		
	}

}
