/*    */ package com.autoscript.ui.core;
/*    */ 
/*    */ import javax.swing.Action;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JButton;
/*    */ 
/*    */ public class ToolBarButton extends JButton
/*    */ {
/*    */   private static final long serialVersionUID = 8387037366371698247L;
/*    */ 
/*    */   public ToolBarButton(ImageIcon icon, Action action, String tipText)
/*    */   {
/* 14 */     super(action);
/* 15 */     setIcon(icon);
/* 16 */     setToolTipText(tipText);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj) {
/* 20 */     return super.equals(obj);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.ToolBarButton
 * JD-Core Version:    0.6.0
 */