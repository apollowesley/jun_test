package com.autoscript.ui.core.reference;

import com.autoscript.ui.exception.ReferenceException;

public abstract interface Reference
{
  public abstract void init()
    throws ReferenceException;

  public abstract void destroy()
    throws ReferenceException;
}

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.reference.Reference
 * JD-Core Version:    0.6.0
 */