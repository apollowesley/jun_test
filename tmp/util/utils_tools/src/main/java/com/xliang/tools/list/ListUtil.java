package com.xliang.tools.list;

import java.util.ArrayList;
import java.util.List;

public class ListUtil
{
    /**
     * 分割List
     * @param list
     * @param pageSize
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize)
    {
        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize;// 页数  
        List<List<T>> listArray = new ArrayList<List<T>>();// 创建list,用来保存分割后的sublist  
        for (int i = 0; i < page; i++)
        {
            // 按照数组大小遍历  
            List<T> subList = new ArrayList<T>();
            for (int j = 0; j < listSize; j++)
            {
                int pageIndex = (j + pageSize) / pageSize;// 当前记录的页码(第几页)  
                if (pageIndex == (i + 1))
                {
                    //当前记录的页码等于要放入的页码时  
                    subList.add(list.get(j)); //放入分割后的list(subList)  
                }
                if ((j + 1) == ((i + 1) * pageSize))
                {
                    // 当放满一页时退出当前循环  
                    break;
                }
            }
            listArray.add(subList);
        }
        return listArray;
    }
    
    /**
     * list转换成String
     * @param list
     * @return
     */
    public static <T> String listToString(List<T> list)
    {
        StringBuffer result = new StringBuffer();
        if (list == null || list.isEmpty())
        {
            return result.toString();
        }
        for (T t : list)
        {
            result.append(t.toString()).append(",");
        }
        return result.substring(0, result.lastIndexOf(","));
    }
    
    /**
     * list分页
     * @param list
     * @param page：第几页
     * @param pageSize：每页大小
     * @param totalPage：总页数
     * @return
     */
    public static List<?> pageList(List<?> list, int page, int pageSize, int totalPage)
    {
        int fromIndex = 0;
        int toIndex = 0;
        if (list == null || list.isEmpty())
        {
            return list;
        }
        if (totalPage >= page && page > 0)
        {
            fromIndex = (page - 1) * pageSize;
            if (page == totalPage)
            {
                toIndex = list.size();
            }
            else
            {
                toIndex = page * pageSize;
            }
        }
        return list.subList(fromIndex, toIndex);
    }
}
