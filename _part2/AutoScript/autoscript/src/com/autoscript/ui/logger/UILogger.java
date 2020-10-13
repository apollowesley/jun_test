/*    */ package com.autoscript.ui.logger;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class UILogger extends Logger
/*    */ {
/* 12 */   private static UILoggerFactory factory = new UILoggerFactory();
/*    */ 
/*    */   UILogger(String name) {
/* 15 */     super(name);
/*    */   }
/*    */ 
/*    */   public void fatal(Object message) {
/* 19 */     super.fatal(message);
/*    */   }
/*    */ 
/*    */   public void fatal(Object message, Throwable t) {
/* 23 */     super.fatal(message, t);
/*    */   }
/*    */ 
/*    */   public void error(Object message) {
/* 27 */     super.error(message);
/*    */   }
/*    */ 
/*    */   public void error(Object message, Throwable t) {
/* 31 */     super.error(message, t);
/*    */   }
/*    */ 
/*    */   public void debug(Object message) {
/* 35 */     if (isDebugEnabled())
/* 36 */       super.debug(message);
/*    */   }
/*    */ 
/*    */   public void debug(Object message, Throwable t)
/*    */   {
/* 41 */     if (isDebugEnabled())
/* 42 */       super.debug(message, t);
/*    */   }
/*    */ 
/*    */   public void info(Object message)
/*    */   {
/* 47 */     if (isInfoEnabled())
/* 48 */       super.info(message);
/*    */   }
/*    */ 
/*    */   public void info(Object message, Throwable t)
/*    */   {
/* 53 */     if (isInfoEnabled())
/* 54 */       super.info(message, t);
/*    */   }
/*    */ 
/*    */   public static Logger getLogger(String name)
/*    */   {
/* 63 */     return Logger.getLogger(name, factory);
/*    */   }
/*    */ 
/*    */   public static Logger getLogger(Class clz)
/*    */   {
/* 71 */     return Logger.getLogger(clz.getName(), factory);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.logger.UILogger
 * JD-Core Version:    0.6.0
 */