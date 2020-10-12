package com.leo.vhr.model;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/1
 * @version: 1.0
 */
public class RespPageBean
{
    private Long total;
    private List<?> data;

    public Long getTotal()
    {
        return total;
    }

    public void setTotal(Long total)
    {
        this.total = total;
    }

    public List<?> getData()
    {
        return data;
    }

    public void setData(List<?> data)
    {
        this.data = data;
    }
}
