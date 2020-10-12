package com.site.util.excel.iptexcel.service;

import java.io.InputStream;

import com.site.common.exception.BusinessException;



/**
 * excel导入基础数据业务层接口-属性
 * @author zhangshuai
 * @date   2015-4-2
 *
 */
public interface ImportPropertyExcelService {
    /**
     * POI:解析Excel文件中的数据并把每行数据封装成一个实体 
     * @param in 文件输入流 
     * @return 
     *
     */
    public void importPropertyByPoi(InputStream in) throws BusinessException;
    
   
}
