/*    */ package com.autoscript.ui.logger;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ import org.apache.log4j.spi.LoggerFactory;
/*    */ import com.autoscript.ui.core.UIConstants;
/*    */ 
/*    */ public class UILoggerFactory
/*    */   implements LoggerFactory
/*    */ {
/*    */   public UILoggerFactory()
/*    */   {
/* 14 */     String propertyFile = UIConstants.CONF_PATH + "/log4j.properties";
/* 15 */     PropertyConfigurator.configure(propertyFile);
/*    */   }
/*    */ 
/*    */   public Logger makeNewLoggerInstance(String name) {
/* 19 */     return new UILogger(name);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.logger.UILoggerFactory
 * JD-Core Version:    0.6.0
 */