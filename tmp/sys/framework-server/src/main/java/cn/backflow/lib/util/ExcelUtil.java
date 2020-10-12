package cn.backflow.lib.util;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class ExcelUtil {

    // 34, 116, 65 绿
    // 102, 171, 179 蓝
    // 204, 227, 230 淡蓝
    private static XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook, Color color) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor foregroundColor = new XSSFColor(color == null ? new Color(102, 171, 179) : color);
        style.setFillForegroundColor(foregroundColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(HSSFColor.WHITE.index);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFFont font = workbook.createFont(); // 生成一个字体
        font.setFontName("Microsoft YaHei");
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font); // 把字体应用到当前的样式
        return style;
    }

    private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook, Color color) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (color != null) {
            XSSFColor foregroundColor = new XSSFColor(color);
            style.setFillForegroundColor(foregroundColor);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(new XSSFColor(new Color(200, 200, 200, 200)));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    public static void export(String title, String[] headers, Collection<Object[]> data, OutputStream out) {

        XSSFWorkbook workbook = new XSSFWorkbook(); // 声明一个工作薄
        XSSFSheet sheet = workbook.createSheet(title); // 生成一个表格
        sheet.setDefaultColumnWidth(15); // 设置表格默认列宽度为15个字节
        sheet.setDefaultRowHeightInPoints(26.25f);

        XSSFCellStyle headerStyle = createHeaderStyle(workbook, null); // 列头样式
        XSSFCellStyle cellStyleOdd = createCellStyle(workbook, null);    // 单元格样式
        XSSFCellStyle cellStyleEven = createCellStyle(workbook, new Color(204, 227, 230));    // 单元格样式

        XSSFDrawing drawing = sheet.getDrawingPatriarch(); // 声明一个画图的顶级管理器

        // XSSFComment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5)); // 定义注释的大小和位置,详见文档
        // comment.setString(new XSSFRichTextString("可以在POI中添加注释！")); // 设置注释内容
        // comment.setAuthor("leno"); // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.

        XSSFRow row = sheet.createRow(0); // 产生表格标题行
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(new XSSFRichTextString(headers[i]));
        }

        int index = 0;
        for (Object[] arr : data) {
            row = sheet.createRow(index + 1);
            for (int col = 0, len = arr.length; col < len; col++) {
                XSSFCell cell = row.createCell(col);
                cell.setCellStyle(index % 2 == 0 ? cellStyleOdd : cellStyleEven);
                Object o = arr[col];
                if (o instanceof Number) {
                    cell.setCellValue(Double.parseDouble(o.toString()));
                } else if (o instanceof Date) {
                    cell.setCellValue((Date) o);
                } else if (o instanceof String) {
                    String value = o.toString();
                    if (value.matches("^//d+(//.//d+)?$")) { // 值如果是数字当作double处理
                        cell.setCellValue(Double.parseDouble(value));
                    } else {
                        cell.setCellValue(new XSSFRichTextString(value));
                    }
                } else if (o instanceof byte[]) { // 图片
                    row.setHeightInPoints(60); // 设置行高为60px
                    sheet.setColumnWidth(col, (int) (35.7 * 80)); // 设置列宽度为80px
                    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, 6, index, 6, index);
                    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                    sheet.createDrawingPatriarch().createPicture(anchor, workbook.addPicture((byte[]) o, XSSFWorkbook.PICTURE_TYPE_JPEG));
                }
            }
            index++;
        }

        for (short i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a map of Cellstyle objects for page building. Note: this method used for XSSF pages
     *
     * @return hashmap of styles
     */
    private static Map<String, XSSFCellStyle> createXStyles(XSSFWorkbook workbook) {

        Map<String, XSSFCellStyle> xstyles = new HashMap<>();

        XSSFCellStyle style = workbook.createCellStyle();

        // create style for cells in header row
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(new Color(0, 52, 94)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setFont(headerFont);
        xstyles.put("header", style);

        // create styles for alternating background color cells
        XSSFFont bodyFont = workbook.createFont();
        bodyFont.setFontHeightInPoints((short) 8);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(new Color(230, 240, 248)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(bodyFont);
        style.setWrapText(true);
        xstyles.put("bg2", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setFont(bodyFont);
        xstyles.put("bg1", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(new Color(255, 193, 193)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(bodyFont);
        style.setWrapText(true);
        xstyles.put("empty", style);

        return xstyles;
    }
}