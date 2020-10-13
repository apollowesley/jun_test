/*    */ package com.autoscript.ui.core.action;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JOptionPane;
/*    */ import com.autoscript.ui.core.BatchUI;
/*    */ import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class SystemExitAction extends AbstractAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private JFrame frame;
/*    */   private BatchUI ui;
/*    */ 
/*    */   public SystemExitAction(BatchUI ui)
/*    */   {
/* 22 */     this.ui = ui;
/* 23 */     this.frame = ui.getFrame();
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {			
/* 27 */     if (JOptionPane.showConfirmDialog(this.frame, UIPropertyHelper.getString("system.exit"), UIPropertyHelper.getString("system.info"), 0) == 0) {
				ui.syncTreeNode(ui.getSelectProjectTreeNode());
				//检查工程是否未存，未存则提示
				if(ui.getProjectModify()){
					if (JOptionPane.showConfirmDialog(this.frame, UIPropertyHelper.getString("system.needSaveProject"), UIPropertyHelper.getString("system.info"), 0) == 0) {
						ui.saveProject();
					}
				}	
/* 28 */       this.ui.destroy();
/* 29 */       System.exit(0);
/*    */     }
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.action.SystemExitAction
 * JD-Core Version:    0.6.0
 */