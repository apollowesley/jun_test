/*    */ package com.autoscript.ui.core;
/*    */ 
/*    */ import java.awt.Insets;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JButton;
/*    */ 
/*    */ public class NavigationButton extends JButton
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public NavigationButton(String text, Icon icon, Action action)
/*    */   {
/* 18 */     super(action);
/* 19 */     setText(text);
/* 20 */     setIcon(icon);
/*    */ 
/* 22 */     setVerticalTextPosition(3);
/*    */ 
/* 24 */     setHorizontalTextPosition(0);
/*    */ 
/* 26 */     setMargin(new Insets(4, 8, 4, 8));
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 31 */     return super.equals(obj);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.NavigationButton
 * JD-Core Version:    0.6.0
 */