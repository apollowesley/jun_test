/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class BatchFileFilter extends FileFilter
/*    */ {
/*    */   private String desc;
/*    */   private String[] extensions;
/*    */ 
/*    */   public BatchFileFilter(String desc, String[] extensions)
/*    */   {
/* 12 */     this.desc = desc;
/* 13 */     this.extensions = extensions;
/*    */   }
/*    */ 
/*    */   public int getExtensionsLength()
/*    */   {
/* 18 */     return this.extensions.length;
/*    */   }
/*    */ 
/*    */   public String getExtensionValue(int index)
/*    */   {
/* 23 */     return this.extensions[index];
/*    */   }
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 28 */     if (file.isDirectory())
/* 29 */       return true;
/* 30 */     if ((file.isFile()) && (file.getName().endsWith(".xml")))
/* 31 */       return true;
/* 32 */     if ((file.isFile()) && (file.getName().endsWith(".jgx")))
/* 33 */       return true;
/* 34 */     return (file.isFile()) && (file.getName().endsWith(".wav"));
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 39 */     return this.desc;
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.BatchFileFilter
 * JD-Core Version:    0.6.0
 */