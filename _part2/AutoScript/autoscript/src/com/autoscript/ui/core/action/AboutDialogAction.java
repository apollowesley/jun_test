/*    */ package com.autoscript.ui.core.action;
/*    */ 
/*    */ import java.awt.event.ActionEvent;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.core.component.AboutDialog;
import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class AboutDialogAction extends AbstractUIAction
/*    */ {
/*    */   private static final long serialVersionUID = 5194690427552295310L;
/*    */ 
/* 16 */   public AboutDialogAction(BatchUI ui) { super(ui); }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 19 */     AboutDialog dlg = new AboutDialog(this.ui.getFrame(), UIPropertyHelper.getString("dialog.about.title"), true);
/* 20 */     dlg.setVisible(true);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.action.AboutDialogAction
 * JD-Core Version:    0.6.0
 */