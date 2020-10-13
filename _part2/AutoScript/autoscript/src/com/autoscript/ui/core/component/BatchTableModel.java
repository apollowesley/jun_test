/*     */ package com.autoscript.ui.core.component;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import com.autoscript.ui.helper.TypeConversionHelper;
/*     */ 
/*     */ public class BatchTableModel extends AbstractTableModel
/*     */   implements TableModelListener
/*     */ {
/*     */   private static final long serialVersionUID = -614166802922232736L;
/*     */   Vector dataList;
/*     */   Vector titleList;
/*     */   HashMap tempDatas;
/*     */   Vector _dataList;
/*     */   protected boolean modifyFlag;
/*     */ 
/*     */   public BatchTableModel(Vector dataList, Vector titleList)
/*     */   {
/*  21 */     this.modifyFlag = false;
/*  22 */     setDataVector(titleList, dataList);
/*     */   }
/*     */ 
/*     */   public BatchTableModel(Object[] titleList, Object[][] dataList)
/*     */   {
/*  27 */     this.modifyFlag = false;
/*  28 */     setDataVector(titleList, dataList);
/*     */   }
/*     */ 
/*     */   public BatchTableModel(String[] titleList, String[][] dataList)
/*     */   {
/*  33 */     this.modifyFlag = false;
/*  34 */     setDataVector(titleList, dataList);
/*     */   }
/*     */ 
/*     */   public BatchTableModel(String[] titleList, HashMap map)
/*     */   {
/*  39 */     this.modifyFlag = false;
/*  40 */     setDataVector(titleList, map);
/*     */   }
/*     */ 
/*     */   public void setDataVector(Vector titleList, Vector dataList)
/*     */   {
/*  45 */     this.dataList = nonNullVector(dataList);
/*  46 */     this.titleList = nonNullVector(titleList);
/*     */   }
/*     */ 
/*     */   public void setDataVector(String[] titleList, HashMap map)
/*     */   {
/*  51 */     setDataVector(TypeConversionHelper.convertToVector(titleList), convertToVector(map));
/*     */   }
/*     */ 
/*     */   public void setDataVector(Object[] titleList, Object[][] dataList)
/*     */   {
/*  56 */     setDataVector(TypeConversionHelper.convertToVector(titleList), TypeConversionHelper.convertToVector(dataList));
/*     */   }
/*     */ 
/*     */   public static Vector nonNullVector(Vector v)
/*     */   {
/*  61 */     return v == null ? new Vector() : v;
/*     */   }
/*     */ 
/*     */   public boolean isCellEditable(int rowIndex, int columnIndex)
/*     */   {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/*  71 */     return this.titleList.size();
/*     */   }
/*     */ 
/*     */   public int getRowCount()
/*     */   {
/*  76 */     return this.dataList.size();
/*     */   }
/*     */ 
/*     */   public String getColumnName(int column)
/*     */   {
/*  81 */     Object id = null;
/*  82 */     if (column < this.titleList.size())
/*  83 */       id = this.titleList.elementAt(column);
/*  84 */     return id != null ? id.toString() : super.getColumnName(column);
/*     */   }
/*     */ 
/*     */   public Object getValueAt(int rowIndex, int columnIndex)
/*     */   {
/*  89 */     Vector rowDatas = (Vector)this.dataList.elementAt(rowIndex);
/*  90 */     return rowDatas.elementAt(columnIndex);
/*     */   }
/*     */ 
/*     */   public Object getRowData(int rowIndex)
/*     */   {
/*  95 */     return this.dataList.elementAt(rowIndex);
/*     */   }
/*     */ 
/*     */   public void setValueAt(Object newValue, int rowIndex, int columnIndex)
/*     */   {
/* 100 */     Vector rowDatas = (Vector)this.dataList.elementAt(rowIndex);
/* 101 */     rowDatas.setElementAt(newValue, columnIndex);
/* 102 */     fireTableCellUpdated(rowIndex, columnIndex);
/*     */   }
/*     */ 
/*     */   protected static Vector convertToVector(HashMap map)
/*     */   {
/* 107 */     if (map == null)
/* 108 */       return null;
/* 109 */     Vector v = new Vector(map.size());
/*     */     Vector values;
/* 111 */     for (Iterator iter = map.keySet().iterator(); iter.hasNext(); v.addElement(values))
/*     */     {
/* 113 */       values = new Vector();
/* 114 */       String key = (String)iter.next();
/* 115 */       values.addElement(key);
/* 116 */       values.addElement((String)map.get(key));
/*     */     }
/*     */ 
/* 119 */     return v;
/*     */   }
/*     */ 
/*     */   public void filterData(String id, boolean isUpper)
/*     */   {
/* 124 */     if (this._dataList != null)
/* 125 */       this.dataList = this._dataList;
/* 126 */     Vector data = new Vector();
/*     */     String regex;

/* 128 */     if (isUpper)
/* 129 */       regex = ".*" + id.toUpperCase() + ".*";
/*     */     else
/* 131 */       regex = ".*" + id.toLowerCase() + ".*";
/* 132 */     for (int i = 0; i < this.dataList.size(); i++)
/*     */     {
/* 134 */       Vector v = (Vector)this.dataList.get(i);
/* 135 */       String _id = (String)v.get(0);
/* 136 */       if (_id.matches(regex)) {
/* 137 */         data.addElement(v);
/*     */       }
/*     */     }
/* 140 */     applyFilter(data);
/*     */   }
/*     */ 
/*     */   public void applyFilter(Vector data)
/*     */   {
/* 145 */     this._dataList = this.dataList;
/* 146 */     this.dataList = data;
/*     */   }
/*     */ 
/*     */   public HashMap getData(HashMap table, HashMap type)
/*     */   {
/* 151 */     HashMap map = new HashMap();
/* 152 */     for (int i = 0; i < this.dataList.size(); i++)
/*     */     {
/* 154 */       String key = (String)((Vector)this.dataList.elementAt(i)).elementAt(0);
/* 155 */       String value = (String)((Vector)this.dataList.elementAt(i)).elementAt(1);
/* 156 */       if ((value != null) && (!value.equals("")) && (table.containsKey(key))) {
/* 157 */         map.put(table.get(key), checkValueType(table.get(key), value, type));
/*     */       }
/*     */     }
/* 160 */     return map;
/*     */   }
/*     */ 
/*     */   public HashMap getData(HashMap table, HashMap type, Vector data)
/*     */   {
/* 165 */     HashMap map = new HashMap();
/* 166 */     for (int i = 0; i < data.size(); i++)
/*     */     {
/* 168 */       String key = (String)((Vector)data.elementAt(i)).elementAt(0);
/* 169 */       String value = (String)((Vector)data.elementAt(i)).elementAt(1);
/* 170 */       if ((value != null) && (!value.equals("")) && (table.containsKey(key))) {
/* 171 */         map.put(table.get(key), checkValueType(table.get(key), value, type));
/*     */       }
/*     */     }
/* 174 */     return map;
/*     */   }
/*     */ 
/*     */   public void insertRow(int row, Vector data)
/*     */   {
/* 179 */     this.dataList.insertElementAt(data, row);
/*     */   }
/*     */ 
/*     */   public void addRow(Vector data)
/*     */   {
/* 184 */     insertRow(getRowCount(), data);
/*     */   }
/*     */ 
/*     */   public void updateRow(int row, Vector data)
/*     */   {
/* 189 */     this.dataList.removeElementAt(row);
/* 190 */     if (row < getRowCount())
/* 191 */       insertRow(row, data);
/*     */     else
/* 193 */       insertRow(getRowCount(), data);
/*     */   }
/*     */ 
/*     */   public void removeRow(int row)
/*     */   {
/* 198 */     if (row < getRowCount())
/* 199 */       this.dataList.remove(row);
/*     */   }
/*     */ 
/*     */   public Vector getDataVector()
/*     */   {
/* 204 */     return this.dataList;
/*     */   }
/*     */ 
/*     */   public void addTableModelListener()
/*     */   {
/* 209 */     addTableModelListener(this);
/*     */   }
/*     */ 
/*     */   public void removeTableModelListener(TableModelListener tablemodellistener)
/*     */   {
/*     */   }
/*     */ 
/*     */   private Object checkValueType(Object key, Object value, HashMap type)
/*     */   {
/* 218 */     if (type.get(key).equals("String"))
/* 219 */       return (String)value;
/* 220 */     if (type.get(key).equals("Double"))
/* 221 */       return new Double((String)value);
/* 222 */     if (type.get(key).equals("Integer")) {
/* 223 */       return new Integer((String)value);
/*     */     }
/* 225 */     return value;
/*     */   }
/*     */ 
/*     */   public void tableChanged(TableModelEvent e)
/*     */   {
/* 230 */     addData((Vector)this.dataList.get(e.getFirstRow()));
/*     */   }
/*     */ 
/*     */   public void addData(Vector data)
/*     */   {
/* 235 */     this.modifyFlag = true;
/* 236 */     if (this.tempDatas == null)
/* 237 */       this.tempDatas = new HashMap();
/* 238 */     String key = (String)data.get(0);
/* 239 */     if (key != null)
/*     */     {
/* 241 */       if (this.tempDatas.containsKey(key))
/* 242 */         this.tempDatas.remove(key);
/* 243 */       this.tempDatas.put(key, data);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isModify()
/*     */   {
/* 249 */     return this.modifyFlag;
/*     */   }
/*     */ 
/*     */   public boolean containsKey(String key)
/*     */   {
/* 254 */     if (this.tempDatas == null) {
/* 255 */       return false;
/*     */     }
/* 257 */     return this.tempDatas.containsKey(key);
/*     */   }
/*     */ 
/*     */   public String getKey()
/*     */   {
/* 262 */     return (String)((Vector)this.dataList.elementAt(0)).elementAt(1);
/*     */   }
/*     */ 
/*     */   public String[][] getData(String key)
/*     */   {
/* 267 */     return TypeConversionHelper.convertToArray((Vector)this.tempDatas.get(key));
/*     */   }
/*     */ 
/*     */   public void remove(String key)
/*     */   {
/* 272 */     this.tempDatas.remove(key);
/*     */   }
/*     */ 
/*     */   public HashMap getTempData()
/*     */   {
/* 277 */     if (this.tempDatas == null)
/* 278 */       this.tempDatas = new HashMap();
/* 279 */     return this.tempDatas;
/*     */   }
/*     */ 
/*     */   public void initModify()
/*     */   {
/* 284 */     this.modifyFlag = false;
/*     */   }
/*     */ 
/*     */   public HashMap getTempData(HashMap table, HashMap type)
/*     */   {
/* 289 */     HashMap tempData = new HashMap();
/* 290 */     if (this.tempDatas == null)
/* 291 */       return null;
/*     */     Object key;
/* 293 */     for (Iterator iter = this.tempDatas.keySet().iterator(); iter.hasNext(); tempData.put(key, getData(table, type, (Vector)this.tempDatas.get(key)))) {
/* 294 */       key = iter.next();
/*     */     }
/* 296 */     return tempData;
/*     */   }
/*     */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.BatchTableModel
 * JD-Core Version:    0.6.0
 */