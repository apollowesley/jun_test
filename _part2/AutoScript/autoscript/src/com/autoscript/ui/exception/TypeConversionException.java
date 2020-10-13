/*    */ package com.autoscript.ui.exception;
/*    */ 
/*    */ public class TypeConversionException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public TypeConversionException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public TypeConversionException(String message)
/*    */   {
/* 14 */     super(message);
/*    */   }
/*    */ 
/*    */   public TypeConversionException(String message, Throwable t) {
/* 18 */     super(message, t);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.exception.TypeConversionException
 * JD-Core Version:    0.6.0
 */