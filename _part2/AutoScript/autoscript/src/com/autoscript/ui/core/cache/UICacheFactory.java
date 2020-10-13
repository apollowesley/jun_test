/*    */ package com.autoscript.ui.core.cache;
/*    */ 
/*    */ import com.autoscript.ui.logger.UILogger;
/*    */ 
/*    */ public final class UICacheFactory
/*    */ {
/* 11 */   private static UILogger logger = (UILogger)UILogger.getLogger(UICacheFactory.class);
/*    */ 
/*    */   public static UICache getUICache(String cacheName) {
/* 14 */     UICache cache = null;
/*    */ 
/* 16 */     logger.info("UICache Start");
/* 17 */     logger.info("UICache 采用 " + cacheName + " 策略进行数据缓存");
/*    */ 
/* 19 */     if (cacheName.equals("HASHMAP"))
/* 20 */       cache = ModulesCache.getInstance();
/*    */     else {
/* 22 */       cache = ModulesCache.getInstance();
/*    */     }
/* 24 */     return cache;
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.cache.UICacheFactory
 * JD-Core Version:    0.6.0
 */