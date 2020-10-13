/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JOptionPane;
/*    */ import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class DialogMessage extends JOptionPane
/*    */ {
/*    */   private static final long serialVersionUID = 8859775755087526696L;
/* 15 */   private static final String TITLE = UIPropertyHelper.getString("system.info");
/*    */   public static final int ERROR_MESSAGE = 0;
/*    */   public static final int INFORMATION_MESSAGE = 1;
/*    */   public static final int WARNING_MESSAGE = 2;
/*    */   public static final int QUESTION_MESSAGE = 3;
/*    */   public static final int DEFAULT_OPTION = -1;
/*    */   public static final int YES_NO_OPTION = 5;
/*    */   public static final int YES_OPTION = 6;
/*    */   public static final int OK_CANCEL_OPTION = 7;
/*    */   public static final int YES_NO_CANCEL_OPTION = 8;
/*    */ 
/*    */   public static int show(Component component, Object message, int optionType)
/*    */   {
/* 43 */     switch (optionType) {
/*    */     case 0:
/* 45 */       JOptionPane.showMessageDialog(component, message, TITLE, 0);
/* 46 */       return 0;
/*    */     case 1:
/* 48 */       JOptionPane.showMessageDialog(component, message, TITLE, 1);
/* 49 */       return 0;
/*    */     case 2:
/* 51 */       JOptionPane.showMessageDialog(component, message, TITLE, 2);
/* 52 */       return 0;
/*    */     case 3:
/* 54 */       JOptionPane.showMessageDialog(component, message, TITLE, 3);
/* 55 */       return 0;
/*    */     case 5:
/* 57 */       int retval = JOptionPane.showConfirmDialog(component, message, TITLE, 0);
/* 58 */       if (retval == 0) {
/* 59 */         return 6;
/*    */       }
/* 61 */       return 1;
/*    */     case 7:
/* 63 */       retval = JOptionPane.showConfirmDialog(component, message, TITLE, 2);
/* 64 */       if (retval == 0) {
/* 65 */         return 0;
/*    */       }
/* 67 */       return 2;
/*    */     case 8:
/* 69 */       retval = JOptionPane.showConfirmDialog(component, message, TITLE, 1);
/* 70 */       if (retval == 0)
/* 71 */         return 6;
/* 72 */       if (retval == 1) {
/* 73 */         return 1;
/*    */       }
/* 75 */       return 2;
/*    */     case 4:
/* 77 */     case 6: } return JOptionPane.showConfirmDialog(component, message, TITLE, -1);
/*    */   }
/*    */ 
/*    */   public static String input(Component component, Object message)
/*    */   {
/* 88 */     return JOptionPane.showInputDialog(component, message, TITLE, 1);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.DialogMessage
 * JD-Core Version:    0.6.0
 */