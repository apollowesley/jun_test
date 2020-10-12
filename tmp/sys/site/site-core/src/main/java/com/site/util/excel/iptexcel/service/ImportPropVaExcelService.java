package com.site.util.excel.iptexcel.service;

import java.io.InputStream;

/**
 * excel导入service层接口--商品属性值
 * @author zhangshuai
 * @date   2015-4-2
 *
 */
public interface ImportPropVaExcelService {

    /**
     * POI:解析Excel文件中的数据并把每行数据封装成一个实体 
     * @param in 文件输入流 
     * @return 
     * 
     */
    public int importPropertyValueByPoi(InputStream in);
        
    }
