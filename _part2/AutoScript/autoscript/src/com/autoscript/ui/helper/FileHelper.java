/*    */ package com.autoscript.ui.helper;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public final class FileHelper
/*    */ {
/*    */   public static boolean checkFileExist(File file)
/*    */   {
/* 19 */     return (file.exists()) && (file.isFile());
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.helper.FileHelper
 * JD-Core Version:    0.6.0
 */