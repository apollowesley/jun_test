package com.autoscript.ui.core.cache;

public abstract interface UICache
{
  public abstract void put(String paramString, Object paramObject);

  public abstract Object get(String paramString);

  public abstract int size();
}

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.cache.UICache
 * JD-Core Version:    0.6.0
 */