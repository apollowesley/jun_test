/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JPopupMenu;
/*    */ 
/*    */ public class UIPopupMenu extends JPopupMenu
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   private HashMap hash = new HashMap();
/*    */ 
/* 20 */   public JMenuItem add(Action a) { JMenuItem jm = null;
/* 21 */     jm = super.add(a);
/* 22 */     int index = getComponentIndex(jm);
/* 23 */     this.hash.put(new Integer(index), a);
/* 24 */     return jm; }
/*    */ 
/*    */   public AbstractAction getActionByIndex(int index) {
/* 27 */     return (AbstractAction)this.hash.get(new Integer(index));
/*    */   }
/*    */ 
/*    */   public void setEnable(Class[] clzs)
/*    */   {
/* 34 */     int n = getComponents().length;
/*    */ 
/* 38 */     for (int i = 0; i < n; i++) {
/* 39 */       Object o = getActionByIndex(i);
/*    */ 
/* 41 */       if ((o instanceof AbstractAction)) {
/* 42 */         AbstractAction action = (AbstractAction)o;
/* 43 */         if (isInClasses(action, clzs))
/* 44 */           action.setEnabled(true);
/*    */         else
/* 46 */           action.setEnabled(false);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private boolean isInClasses(AbstractAction hitaction, Class[] clzs)
/*    */   {
/* 58 */     int l = clzs.length;
/* 59 */     for (int i = 0; i < l; i++) {
/* 60 */       if (hitaction.getClass().getName().equals(clzs[i].getName()))
/* 61 */         return true;
/*    */     }
/* 63 */     return false;
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.UIPopupMenu
 * JD-Core Version:    0.6.0
 */