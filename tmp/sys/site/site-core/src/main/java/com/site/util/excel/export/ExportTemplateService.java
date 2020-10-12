package com.site.util.excel.export;

import java.util.List;

import com.site.common.exception.BusinessException;

/**
 * 导出excel service接口
 * 
 * @author zhangshuai
 * @date Mar 17, 2015 4:07:35 PM
 *
 */
public interface ExportTemplateService {

    /**
     * 返回导出excel流
     * 
     * @param dataRows
     *            需要导出的list集合
     * @param clazz
     *            list中泛型类型
     * @param exportTemplate
     *            导出excel模板对应的key值
     * @param propertyNames
     *            需要导出的字段
     * @return excel byte流
     * @throws BusinessException
     */
    public byte[] getOutByte(List<?> dataRows, Class<?> clazz, String exportTemplate, String propertyNames) throws BusinessException;

    /**
     * 返回导出excel流
     * 
     * @param dataRows
     *            需要导出的list集合
     * @param clazz
     *            list中泛型类型
     * @param exportTemplate
     *            导出excel模板对应的key值
     * @param propertyNames
     *            需要导出的字段
     * @return excel byte流
     * @return startRow 数据填充开始行
     * @throws BusinessException
     */
    public byte[] getOutByte(List<?> dataRows, Class<?> clazz, String exportTemplate, String propertyNames, int startRowIndex) throws BusinessException;

}
