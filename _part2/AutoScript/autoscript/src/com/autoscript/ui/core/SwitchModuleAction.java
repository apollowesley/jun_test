/*    */ package com.autoscript.ui.core;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.SwingUtilities;
/*    */ import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class SwitchModuleAction extends AbstractAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private BatchUI ui;
/*    */   private Class moduleClass;
/*    */   private long startTime;
/*    */ 
/*    */   public SwitchModuleAction(BatchUI ui, Class moduleClass)
/*    */   {
/* 21 */     this.ui = ui;
/* 22 */     this.moduleClass = moduleClass;
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 26 */     this.startTime = System.currentTimeMillis();
/* 27 */     this.ui.getStatusField().setText(UIPropertyHelper.getString("system.status.change"));
/* 28 */     SwingUtilities.invokeLater(new Runnable() {
/*    */       public void run() {
/* 30 */         OperationModule obj = SwitchModuleAction.this.ui.getCurrentModule();
/* 31 */         obj.setVisible(false);
/* 32 */         OperationModule module = SwitchModuleAction.this.ui.getModule(SwitchModuleAction.this.moduleClass);
/* 33 */         module.setVisible(false);
/* 34 */         SwitchModuleAction.this.ui.getMainPane().remove(obj);
/* 35 */         SwitchModuleAction.this.ui.getMainPane().add(module, "Center");
/* 36 */         module.setVisible(true);
/* 37 */         SwitchModuleAction.this.ui.setCurrentModule(module);
/* 38 */         long endTime = System.currentTimeMillis();
/* 39 */         long spendTime = (endTime - SwitchModuleAction.this.startTime) / 1000L;
/* 40 */         SwitchModuleAction.this.ui.getStatusField().setText(UIPropertyHelper.getString("system.status.spendtiem") + spendTime + "s)");
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.SwitchModuleAction
 * JD-Core Version:    0.6.0
 */