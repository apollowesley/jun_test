/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.table.DefaultTableCellRenderer;
/*    */ import javax.swing.table.DefaultTableColumnModel;
/*    */ import javax.swing.table.TableColumn;
/*    */ 
/*    */ public class BatchColumnModel extends DefaultTableColumnModel
/*    */ {
/*    */   private static final long serialVersionUID = -7710003861042409474L;
/*    */   protected boolean firstTitle;
/*    */ 
/*    */   public BatchColumnModel()
/*    */   {
/* 17 */     this.firstTitle = true;
/*    */   }
/*    */ 
/*    */   public void addColumn(TableColumn column)
/*    */   {
/* 22 */     if (this.firstTitle)
/*    */     {
/* 24 */       DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
/* 25 */       renderer.setBackground(Color.LIGHT_GRAY);
/* 26 */       column.setCellRenderer(renderer);
/* 27 */       column.setMaxWidth(200);
/* 28 */       super.addColumn(column);
/* 29 */       this.firstTitle = false;
/*    */     }
/*    */     else {
/* 32 */       column.setMaxWidth(2000);
/* 33 */       super.addColumn(column);
/*    */     }
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.BatchColumnModel
 * JD-Core Version:    0.6.0
 */