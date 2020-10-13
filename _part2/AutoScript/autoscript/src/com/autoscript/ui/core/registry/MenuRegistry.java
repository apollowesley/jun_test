/*     */ package com.autoscript.ui.core.registry;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ 
/*     */ public class MenuRegistry
/*     */ {
/*     */   private static MenuRegistry instance;
/*  19 */   private Map menus = new HashMap();
/*  20 */   private List menuList = new LinkedList();
/*     */ 
/*     */   public static MenuRegistry getInstance()
/*     */   {
/*  25 */     if (instance == null) {
/*  26 */       instance = new MenuRegistry();
/*     */     }
/*  28 */     return instance;
/*     */   }
/*     */ 
/*     */   private void update(String key, Component component) {
/*  32 */     int index = this.menuList.indexOf(this.menus.get(key));
/*  33 */     this.menuList.remove(index);
/*  34 */     this.menus.remove(key);
/*  35 */     this.menus.put(key, component);
/*  36 */     this.menuList.add(index, this.menus.get(key));
/*     */   }
/*     */ 
/*     */   public void registry(String key, String menuName)
/*     */   {
/*  45 */     if (this.menus.containsKey(key)) {
/*  46 */       JMenu menu = new JMenu(menuName);
/*  47 */       if (!this.menus.get(key).equals(menu))
/*  48 */         update(key, menu);
/*     */     }
/*     */     else {
/*  51 */       JMenu menu = new JMenu(menuName);
/*  52 */       this.menus.put(key, menu);
/*  53 */       this.menuList.add(menu);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void registryItem(String parent, String label, char mnemonic, String accessibleDescription, boolean separator, Action action)
/*     */   {
/*  66 */     if (this.menus.containsKey(parent)) {
/*  67 */       JMenu menu = (JMenu)this.menus.get(parent);
/*  68 */       createMenuItem(menu, label, mnemonic, accessibleDescription, separator, action);
/*     */     }
/*     */   }
/*     */ 
/*     */   private JMenuItem createMenuItem(JMenu menu, String label, char mnemonic, String accessibleDescription, boolean separator, Action action)
/*     */   {
/*  82 */     if (separator) {
/*  83 */       menu.addSeparator();
/*     */     }
/*  85 */     JMenuItem mi = menu.add(new JMenuItem(label));
/*  86 */     mi.setMnemonic(mnemonic);
/*  87 */     mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
/*  88 */     mi.addActionListener(action);
/*  89 */     return mi;
/*     */   }
/*     */ 
/*     */   public Map getMenuRegistry() {
/*  93 */     return this.menus;
/*     */   }
/*     */ 
/*     */   public List getMenuList() {
/*  97 */     return this.menuList;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 101 */     return this.menus.size();
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 105 */     this.menus.clear();
/*     */   }
/*     */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.registry.MenuRegistry
 * JD-Core Version:    0.6.0
 */