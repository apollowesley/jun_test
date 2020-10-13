/*    */ package com.autoscript.ui.core;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.FlowLayout;
/*    */ import javax.swing.JLabel;
/*    */ import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class WelcomePanel extends OperationModule
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public WelcomePanel(BatchUI ui)
/*    */   {
/* 17 */     super(ui, "WelcomePanel");
/* 18 */     setLayout(new FlowLayout(1, 0, 200));
/* 19 */     setBackground(Color.WHITE);
/* 20 */     JLabel label = new JLabel(UIPropertyHelper.getString("welcome.title"));
/* 21 */     label.setAlignmentY(0.5F);
/* 22 */     add(label);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.WelcomePanel
 * JD-Core Version:    0.6.0
 */