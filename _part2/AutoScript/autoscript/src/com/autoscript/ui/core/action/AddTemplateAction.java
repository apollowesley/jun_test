package com.autoscript.ui.core.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.helper.UIPropertyHelper;

public class AddTemplateAction extends AbstractUIAction
{
  private static final long serialVersionUID = 1L;
 public AddTemplateAction(BatchUI ui)
  {
     super(ui);
  }

  public void setBatchUI(BatchUI ui)
  {
    this.ui = ui;
  }



  public void actionPerformed(ActionEvent e) {
	  String nodeName = JOptionPane.showInputDialog(UIPropertyHelper.getString("system.input_template_name"));
	  if(nodeName!=null&& nodeName.trim().length()>0){
		  ui.createTemplateTreeNode(nodeName);
		  //设置工程修改标志
		  ui.setProjectModify(true);
	  }
  }
  
  public AddTemplateAction(String text, Icon icon, String description, char accelerator) {
    super(text, icon);

    putValue("AcceleratorKey", KeyStroke.getKeyStroke(accelerator));

    putValue("ShortDescription", description);
  }
}
