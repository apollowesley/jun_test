package boot.spring.poi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class WriteExcel {
    //导出表的列名
    private String[] rowName;
    //每行作为一个Object对象
    private List<Object[]> dataList = new ArrayList<Object[]>();

    //构造方法，传入要导出的数据
    public WriteExcel(String[] rowName, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
    }

    /*
     * 导出数据
     * */
    public InputStream export() throws Exception {
        return null;
    }

    /*
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        return null;
    }

    /*
     * 列数据信息单元格样式
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        return null;
    }
}
