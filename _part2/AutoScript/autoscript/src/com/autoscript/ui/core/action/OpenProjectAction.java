/**
 * 
 */
package com.autoscript.ui.core.action;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.core.filter.XmlFilenameFilter;
import com.autoscript.ui.helper.SerialHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.projecttree.TreeRootModel;

/**
 * 打开工程Action
 * 作者:龙色波
 * 日期:2013-10-10
 */
public class OpenProjectAction extends AbstractUIAction {

	public OpenProjectAction(BatchUI ui) {
		super(ui);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3882032380476779079L;

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		FileDialog dlg;
		dlg = new FileDialog(ui.getFrame(),UIPropertyHelper.getString("dialog.openproject.title"),FileDialog.LOAD);
		dlg.setFilenameFilter(new XmlFilenameFilter());
		dlg.setVisible(true);
		String fileName;
		fileName = dlg.getDirectory()+dlg.getFile();
		if(dlg.getFile()!=null){			
			try {
				TreeRootModel rootModel;
				rootModel = (TreeRootModel) SerialHelper.readFromXml(fileName);
				ui.buildProjectTree(rootModel);
				ui.setProjectFileName(fileName);
				//打开文件，设置工程修改标志为false
				ui.setProjectModify(false);
			} catch (FileNotFoundException e1) {
				logger.error(e1.getMessage(), e1);
				e1.printStackTrace();
			}
		}

	}
	public OpenProjectAction(String text, Icon icon, char accelerator, String description) {
		    super(text, icon);

		    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

		    putValue("ShortDescription", description);
		  }
}
