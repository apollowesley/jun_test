package org.neuedu.his.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @apiNote : 用于接收 解析 Excel 的行数据
 * @author : CDHONG.IT
 * @date : 2020/4/7
 * @version 1.0
 */
@Data
@HeadRowHeight(30)
@ContentRowHeight(26)
public class DrugsDTO {

    @ColumnWidth(16)
    @ExcelProperty(value = "药品编码")
    private String drugsCode;

    @ColumnWidth(26)
    @ExcelProperty(value = "药品名称")
    private String drugsName;

    @ColumnWidth(18)
    @ExcelProperty(value = "药品规格")
    private String drugsFormat;

    @ColumnWidth(14)
    @ExcelProperty(value = "药品剂型")
    private String drugsDosage;

    @ColumnWidth(14)
    @ExcelProperty(value = "药品类型")
    private String drugsType;

    @ColumnWidth(14)
    @ExcelProperty(value = "药品单价")
    private Double drugsPrice;

    @ColumnWidth(18)
    @ExcelProperty(value = "拼音助记码")
    private String mnemonicCode;

    @ColumnWidth(14)
    @ExcelProperty(value = "包装单位")
    private String drugsUnit;

}
