/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.util.HashMap;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JToolBar;
/*    */ 
/*    */ public class UIToolBar extends JToolBar
/*    */ {
/*    */   private static final long serialVersionUID = -7789861023185033307L;
/* 19 */   private HashMap hash = new HashMap();
/* 20 */   private HashMap hashbutton = new HashMap();
/*    */ 
/* 22 */   public JButton add(Action a) { JButton jb = null;
/* 23 */     jb = super.add(a);
/* 24 */     int index = getComponentIndex(jb);
/* 25 */     this.hash.put(new Integer(index), a);
/* 26 */     return jb; }
/*    */ 
/*    */   public Component add(Component c) {
/* 29 */     Component rc = null;
/* 30 */     rc = super.add(c);
/* 31 */     int index = getComponentIndex(rc);
/* 32 */     if ((c instanceof AbstractButton))
/* 33 */       this.hashbutton.put(new Integer(index), rc);
/* 34 */     return rc;
/*    */   }
/*    */ 
/*    */   public AbstractAction getActionByIndex(int index)
/*    */   {
/* 42 */     return (AbstractAction)this.hash.get(new Integer(index));
/*    */   }
/*    */ 
/*    */   public AbstractButton getButtonByIndex(int index)
/*    */   {
/* 50 */     return (AbstractButton)this.hashbutton.get(new Integer(index));
/*    */   }
/*    */ 
/*    */   public void setEnable(Class[] clzs)
/*    */   {
/* 57 */     int n = getComponents().length;
/*    */ 
/* 61 */     for (int i = 0; i < n; i++) {
/* 62 */       Object o = getActionByIndex(i);
/*    */ 
/* 64 */       if ((o instanceof AbstractAction)) {
/* 65 */         AbstractAction action = (AbstractAction)o;
/* 66 */         if (isInClasses(action, clzs))
/* 67 */           action.setEnabled(true);
/*    */         else
/* 69 */           action.setEnabled(false);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private boolean isInClasses(AbstractAction hitaction, Class[] clzs)
/*    */   {
/* 81 */     int l = clzs.length;
/* 82 */     for (int i = 0; i < l; i++) {
/* 83 */       if (hitaction.getClass().getName().equals(clzs[i].getName()))
/* 84 */         return true;
/*    */     }
/* 86 */     return false;
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.UIToolBar
 * JD-Core Version:    0.6.0
 */