/*    */ package com.autoscript.ui.core.registry;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JButton;
/*    */ import com.autoscript.ui.core.BatchUI;
/*    */ import com.autoscript.ui.core.NavigationButton;
/*    */ import com.autoscript.ui.core.SwitchModuleAction;
/*    */ 
/*    */ public class NavigationRegistry
/*    */ {
/*    */   private static NavigationRegistry instance;
/* 20 */   private Map navigations = new HashMap();
/*    */ 
/*    */   public static NavigationRegistry getInstance()
/*    */   {
/* 25 */     if (instance == null) {
/* 26 */       instance = new NavigationRegistry();
/*    */     }
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   private void update(String key, Object obj) {
/* 32 */     this.navigations.remove(key);
/* 33 */     this.navigations.put(key, obj);
/*    */   }
/*    */ 
/*    */   public void registry(BatchUI ui, String title, ImageIcon image, Class actionClass)
/*    */   {
/* 44 */     if (this.navigations.containsKey(title)) {
/* 45 */       Action action = new SwitchModuleAction(ui, actionClass);
/* 46 */       JButton button = new NavigationButton(title, image, action);
/* 47 */       if (!this.navigations.get(title).equals(button))
/* 48 */         update(title, button);
/*    */     }
/*    */     else {
/* 51 */       Action action = new SwitchModuleAction(ui, actionClass);
/* 52 */       JButton button = new NavigationButton(title, image, action);
/* 53 */       this.navigations.put(title, button);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Map getNavigationRegistry() {
/* 58 */     return this.navigations;
/*    */   }
/*    */ 
/*    */   public int size() {
/* 62 */     return this.navigations.size();
/*    */   }
/*    */ 
/*    */   public void clear() {
/* 66 */     this.navigations.clear();
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.registry.NavigationRegistry
 * JD-Core Version:    0.6.0
 */