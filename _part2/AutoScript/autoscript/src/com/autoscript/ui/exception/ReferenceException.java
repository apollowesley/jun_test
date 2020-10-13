/*    */ package com.autoscript.ui.exception;
/*    */ 
/*    */ public class ReferenceException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -5339875113744231726L;
/*    */ 
/*    */   public ReferenceException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ReferenceException(String m)
/*    */   {
/* 14 */     super(m);
/*    */   }
/*    */ 
/*    */   public ReferenceException(String m, Throwable t) {
/* 18 */     super(m, t);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.exception.ReferenceException
 * JD-Core Version:    0.6.0
 */