/**
 * 
 */
package com.autoscript.ui.core.render;

/**
 *
 * 作者:龙色波
 * 日期:2014-4-28
 */

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.autoscript.ui.helper.ImageHelper;
import com.autoscript.ui.model.projecttree.ITreeModel;
import com.autoscript.ui.model.projecttree.TemplateModel;

/**
 * 工程树渲染器
 * 
 * 作者:龙色波 日期:2014-4-28
 */
public class ProjectTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 307228068046324970L;
	private ImageHelper image;

	public ProjectTreeCellRenderer(ImageHelper image) {
		this.image = image;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		ITreeModel model = (ITreeModel) node.getUserObject();
		switch (model.getType()) {
		// 根节点
		case ITreeModel.ROOT_TYPE:
			if (expanded) {
				this.setIcon(image.OPEN_FOLDER_ENABLE_ICON);
			} else {
				this.setIcon(image.FOLDER_ENABLE_ICON);
			}
			break;
		// 模板节点
		case ITreeModel.TEMPLATE_TYPE:
			TemplateModel templateModel = (TemplateModel) model;
			if (expanded) {
				// 如果启用
				if (templateModel.isEnable()) {
					this.setIcon(image.OPEN_FOLDER_ENABLE_ICON);
				} else {
					this.setIcon(image.OPEN_FOLDER_DISABLE_ICON);
				}
			} else {
				// 如果启用
				if (templateModel.isEnable()) {
					this.setIcon(image.FOLDER_ENABLE_ICON);
				} else {
					this.setIcon(image.FOLDER_DISABLE_ICON);
				}
			}
			break;
		// 其他节点
		default:
			this.setIcon(image.FILE_ICON);
			break;
		}
		return this;
	}

}
