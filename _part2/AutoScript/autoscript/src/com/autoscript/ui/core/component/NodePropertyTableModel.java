/*     */ package com.autoscript.ui.core.component;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import com.autoscript.ui.helper.TypeConversionHelper;
/*     */ 
/*     */ public class NodePropertyTableModel extends BatchTableModel
/*     */ {
/*     */   private static final long serialVersionUID = 6360002795945276178L;
/*     */ 
/*     */   public NodePropertyTableModel(Vector dataList, Vector titleList)
/*     */   {
/*  21 */     super(titleList, dataList);
/*     */   }
/*     */ 
/*     */   public NodePropertyTableModel(String[] titleList, String[][] dataList)
/*     */   {
/*  26 */     super(titleList, dataList);
/*     */   }
/*     */ 
/*     */   public NodePropertyTableModel(String[] titleList, HashMap map)
/*     */   {
/*  31 */     super(titleList, map);
/*     */   }
/*     */ 
/*     */   public boolean isCellEditable(int rowIndex, int columnIndex)
/*     */   {
/*  36 */     return columnIndex != 0;
/*     */   }
/*     */ 
/*     */   public void tableChanged(TableModelEvent e)
/*     */   {
/*  41 */     addData(this.dataList);
/*     */   }
/*     */ 
/*     */   public void addData(Vector dataList)
/*     */   {
/*  46 */     this.modifyFlag = true;
/*  47 */     if (this.tempDatas == null)
/*  48 */       this.tempDatas = new HashMap();
/*  49 */     String key = (String)((Vector)dataList.elementAt(0)).elementAt(1);
/*  50 */     if (key != null)
/*     */     {
/*  52 */       if (this.tempDatas.containsKey(key))
/*  53 */         this.tempDatas.remove(key);
/*  54 */       this.tempDatas.put(key, dataList);
/*     */     }
/*     */     else {
/*  57 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initModify()
/*     */   {
/*  63 */     this.modifyFlag = false;
/*     */   }
/*     */ 
/*     */   public boolean isModify()
/*     */   {
/*  68 */     return this.modifyFlag;
/*     */   }
/*     */ 
/*     */   public boolean containsKey(String key)
/*     */   {
/*  73 */     if (this.tempDatas == null) {
/*  74 */       return false;
/*     */     }
/*  76 */     return this.tempDatas.containsKey(key);
/*     */   }
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  81 */     return (String)((Vector)this.dataList.elementAt(0)).elementAt(1);
/*     */   }
/*     */ 
/*     */   public String[][] getData(String key)
/*     */   {
/*  86 */     return TypeConversionHelper.convertToArray((Vector)this.tempDatas.get(key));
/*     */   }
/*     */ 
/*     */   public Vector getDataVector(String key)
/*     */   {
/*  91 */     return (Vector)this.tempDatas.get(key);
/*     */   }
/*     */ 
/*     */   public void remove(String key)
/*     */   {
/*  96 */     if (this.tempDatas == null)
/*  97 */       return;
/*  98 */     if (this.tempDatas.containsKey(key))
/*     */     {
/* 100 */       this.tempDatas.remove(key);
/* 101 */       if (getTempSize() == 0)
/* 102 */         initModify();
/*     */     }
/*     */     else {
/* 105 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTempSize()
/*     */   {
/* 111 */     return this.tempDatas.size();
/*     */   }
/*     */ 
/*     */   public HashMap getTempData(String key, HashMap table, HashMap type)
/*     */   {
/* 116 */     if ((this.tempDatas == null) || (!this.tempDatas.containsKey(key))) {
/* 117 */       return getData(table, type, this.dataList);
/*     */     }
/* 119 */     return getData(table, type, (Vector)this.tempDatas.get(key));
/*     */   }
/*     */ 
/*     */   public HashMap getTempData(HashMap table, HashMap type)
/*     */   {
/* 124 */     HashMap tempData = new HashMap();
/* 125 */     if (this.tempDatas == null)
/* 126 */       return null;
/*     */     Object key;
/* 128 */     for (Iterator iter = this.tempDatas.keySet().iterator(); iter.hasNext(); tempData.put(key, getData(table, type, (Vector)this.tempDatas.get(key)))) {
/* 129 */       key = iter.next();
/*     */     }
/* 131 */     return tempData;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 136 */     if (this.tempDatas != null)
/* 137 */       this.tempDatas.clear();
/*     */   }
/*     */ 
/*     */   public void setModifyFlag(boolean value)
/*     */   {
/* 144 */     this.modifyFlag = value;
/*     */   }
/*     */ 
/*     */   public void setValue(String key, String value)
/*     */   {
/* 154 */     for (int i = 0; i < this.dataList.size(); i++)
/*     */     {
/* 156 */       String cmpKey = (String)((Vector)this.dataList.elementAt(i)).elementAt(0);
/* 157 */       String cmpValue = (String)((Vector)this.dataList.elementAt(i)).elementAt(1);
/* 158 */       if ((!cmpKey.equals(key)) || 
/* 159 */         (cmpValue.equals(value))) continue;
/* 160 */       super.setValueAt(value, i, 1);
/*     */     }
/*     */   }
/*     */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.NodePropertyTableModel
 * JD-Core Version:    0.6.0
 */