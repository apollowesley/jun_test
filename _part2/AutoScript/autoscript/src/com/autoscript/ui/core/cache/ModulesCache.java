/*    */ package com.autoscript.ui.core.cache;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public final class ModulesCache
/*    */   implements UICache
/*    */ {
/*    */   private static ModulesCache instance;
/* 11 */   private HashMap modules = new HashMap();
/*    */ 
/*    */   public static ModulesCache getInstance()
/*    */   {
/* 17 */     if (instance == null) {
/* 18 */       instance = new ModulesCache();
/*    */     }
/* 20 */     return instance;
/*    */   }
/*    */ 
/*    */   public void put(String key, Object obj) {
/* 24 */     this.modules.put(key, obj);
/*    */   }
/*    */ 
/*    */   public Object get(String key) {
/* 28 */     return this.modules.get(key);
/*    */   }
/*    */ 
/*    */   public int size() {
/* 32 */     return this.modules.size();
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.cache.ModulesCache
 * JD-Core Version:    0.6.0
 */