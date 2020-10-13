package com.autoscript.ui.core.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import com.autoscript.ui.helper.TypeConversionHelper;

public class AutoSriptTableModel extends BatchTableModel
{
  private static final long serialVersionUID = 6360002795945276178L;
  private boolean modifyFlag;

  public AutoSriptTableModel(Vector dataList, Vector titleList)
  {
    super(titleList, dataList);
  }

  public AutoSriptTableModel(String[] titleList, String[][] dataList)
  {
    super(titleList, dataList);
  }

  public AutoSriptTableModel(String[] titleList, HashMap map)
  {
    super(titleList, map);
  }

  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return true;
  }

  public void tableChanged(TableModelEvent e)
  {
    addData(this.dataList);
  }

  public void addData(Vector dataList)
  {
    this.modifyFlag = true;
    if (this.tempDatas == null)
      this.tempDatas = new HashMap();
    String key = (String)((Vector)dataList.elementAt(0)).elementAt(1);
    if (key != null)
    {
      if (this.tempDatas.containsKey(key))
        this.tempDatas.remove(key);
      this.tempDatas.put(key, dataList);
    }
    else {
      return;
    }
  }

  public void initModify()
  {
    this.modifyFlag = false;
  }

  public boolean isModify()
  {
    return this.modifyFlag;
  }

  public boolean containsKey(String key)
  {
    if (this.tempDatas == null) {
      return false;
    }
    return this.tempDatas.containsKey(key);
  }

  public String getKey()
  {
    return (String)((Vector)this.dataList.elementAt(0)).elementAt(1);
  }

  public String[][] getData(String key)
  {
    return TypeConversionHelper.convertToArray((Vector)this.tempDatas.get(key));
  }

  public Vector getDataVector(String key)
  {
    return (Vector)this.tempDatas.get(key);
  }

  public void remove(String key)
  {
    if (this.tempDatas == null)
      return;
    if (this.tempDatas.containsKey(key))
    {
      this.tempDatas.remove(key);
      if (getTempSize() == 0)
        initModify();
    }
    else {
      return;
    }
  }

  public int getTempSize()
  {
    return this.tempDatas.size();
  }

  public HashMap getTempData(String key, HashMap table, HashMap type)
  {
    if ((this.tempDatas == null) || (!this.tempDatas.containsKey(key))) {
      return getData(table, type, this.dataList);
    }
    return getData(table, type, (Vector)this.tempDatas.get(key));
  }

  public HashMap getTempData(HashMap table, HashMap type)
  {
    HashMap tempData = new HashMap();
    if (this.tempDatas == null)
      return null;
    Object key;
    for (Iterator iter = this.tempDatas.keySet().iterator(); iter.hasNext(); tempData.put(key, getData(table, type, (Vector)this.tempDatas.get(key)))) {
      key = iter.next();
    }
    return tempData;
  }

  public void clear()
  {
    if (this.tempDatas != null)
      this.tempDatas.clear();
  }
}

