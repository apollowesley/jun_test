/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ public class WatchPropertyTableModel extends BatchTableModel
/*    */ {
/*    */   private static final long serialVersionUID = -445373990563106994L;
/*    */ 
/*    */   public WatchPropertyTableModel(String[] titleList, String[][] dataList)
/*    */   {
/* 10 */     super(titleList, dataList);
/*    */   }
/*    */ 
/*    */   public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 14 */     return false;
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.WatchPropertyTableModel
 * JD-Core Version:    0.6.0
 */