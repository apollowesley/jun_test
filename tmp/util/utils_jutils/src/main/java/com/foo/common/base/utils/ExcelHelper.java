package com.foo.common.base.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Excel Helper class.
 * <p>See the book of: <a href="http://poi.apache.org/spreadsheet/quick-guide.html#NewWorkbook">NewWorkbook Of Poi Quick-Guide</a>
 *®
 * @author Steve
 */
public class ExcelHelper {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(ExcelHelper.class);

    @Test
    public void test_getExcelColumnLabel_to_getExcelColumn() {
        ImmutableList.of("aa", "z", "bz", "zz", "a").forEach((source) -> {
            int excelColumn = FooUtils.getExcelColumn(source);
            String excelColumnLabel = FooUtils.getExcelColumnLabel(excelColumn);
            logger.info("source:【{}】,excelColumn:【{}】,excelColumnLabel:【{}】",
                    source, excelColumn,
                    excelColumnLabel);
            Assert.assertTrue(excelColumnLabel.equalsIgnoreCase(source));
        });
    }

    @Test
    public void readingExcel() throws Exception {
        File testExcelDir = FooUtils.getClassPathResourceFile("excels");
        Collection<File> payrollFiles = FileUtils.listFiles(testExcelDir, new String[]{"xls", "xlsx"}, false);
        for (File testExcel : payrollFiles) {
            logger.info("dealing file:{}", testExcel.getName());
            InputStream inputStream = new FileInputStream(testExcel.getPath());
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            Row row;
            Cell cell;
            int rowNum;
            int emptyRowCount = 0;
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    emptyRowCount++;
                    continue;
                }
                rowNum = row.getRowNum();
//                if (FooUtils.getExcelCellValue(row.getCell(0)).equals("")) {
//                    logger.warn("find costCenter empty value on row:{}.Just break it.", i + 1);
//                    break;
//                }
                if (rowNum == 0) {
                    continue;
                }
                //In real product environment,you should use row.getCell(cellIndex) instead of using loop for effective.
                //The following is just for example.
                for (int columnNum = 0; columnNum < row.getLastCellNum(); columnNum++) {
                    cell = row.getCell(columnNum);
                    logger.info("coordinate:【{}{}】,value:【{}】",
                            FooUtils.getExcelColumnLabel(columnNum), (rowNum + 1),
                            FooUtils.getExcelCellValue(cell));
                }
            }
            inputStream.close();
            logger.info("emptyRowCount:【{}】", emptyRowCount);
        }
    }

    @Test
    public void writeExcel() throws Exception {
        //TODO
    }

    /**
     * 工业平台生成带样式的excel，撰写大数据。使用类：SXSSFWorkbook
     */
    @Test
    public void createExcelWithStyles() throws Exception {

        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sheet = wb.createSheet("对账单明细");

        List<String> s_titleNames = Lists.newArrayList();
        s_titleNames.add("NO");
        s_titleNames.add("订单日期");
        s_titleNames.add("供应商名称");
        s_titleNames.add("供应商订单号码");
        s_titleNames.add("品牌");
        s_titleNames.add("品名");
        s_titleNames.add("型号");
        s_titleNames.add("数量");
        s_titleNames.add("含税单价");
        s_titleNames.add("含税单价差额");
        s_titleNames.add("最终含税单价");
        s_titleNames.add("最终含税总价");
        s_titleNames.add("发货日期");
        s_titleNames.add("对账时间");
        s_titleNames.add("流水号");
        s_titleNames.add("发票日期");
        s_titleNames.add("发票号码");
        s_titleNames.add("收款时间");
        s_titleNames.add("收款金额");
        s_titleNames.add("备注");

        List<String> c_titleNames = Lists.newArrayList();
        c_titleNames.add("NO");
        c_titleNames.add("订单日期");
        c_titleNames.add("客户名称");
        c_titleNames.add("客户订单号码");
        c_titleNames.add("品牌");
        c_titleNames.add("客户采购单号");
        c_titleNames.add("品名");
        c_titleNames.add("型号");
        c_titleNames.add("数量");
        c_titleNames.add("含税单价");
        c_titleNames.add("含税单价差额");
        c_titleNames.add("最终含税单价");
        c_titleNames.add("最终含税总价");
        c_titleNames.add("发货日期");
        c_titleNames.add("对账时间");
        c_titleNames.add("流水号");
        c_titleNames.add("发票日期");
        c_titleNames.add("发票号码");
        c_titleNames.add("收款时间");
        c_titleNames.add("收款金额");
        c_titleNames.add("备注");

        sheet.addMergedRegion(
                new CellRangeAddress(0, 0, 0, s_titleNames.size() - 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, s_titleNames.size(),
                s_titleNames.size() + c_titleNames.size() - 1));

        Row row;
        Cell cell;

        sheet.setDefaultColumnWidth(20);

        row = sheet.createRow(0);
        Font title_one_font = wb.createFont();
        title_one_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        title_one_font.setFontHeightInPoints((short) 16);
        XSSFCellStyle title_one_style = (XSSFCellStyle) wb.createCellStyle();
        title_one_style.setAlignment(CellStyle.ALIGN_CENTER);
        title_one_style
                .setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        title_one_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        title_one_style.setFont(title_one_font);
        cell = row.createCell(0);
        cell.setCellValue("对账单明细(供应商)");
        cell.setCellStyle(title_one_style);

        title_one_style = (XSSFCellStyle) wb.createCellStyle();
        title_one_style.setAlignment(CellStyle.ALIGN_CENTER);
        title_one_style
                .setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        title_one_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        title_one_style.setFont(title_one_font);
        cell = row.createCell(s_titleNames.size());
        cell.setCellValue("对账单明细(客户)");
        cell.setCellStyle(title_one_style);

        row = sheet.createRow(1);
        Font title_two_font = wb.createFont();
        title_two_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        title_two_font.setFontHeightInPoints((short) 10);
        XSSFCellStyle title_two_style = (XSSFCellStyle) wb.createCellStyle();
        title_two_style.setAlignment(CellStyle.ALIGN_CENTER);
        title_two_style.setFont(title_two_font);

        int titleCellIndex = 0;
        for (String titleName : s_titleNames) {
            cell = row.createCell(titleCellIndex++);
            cell.setCellValue(titleName);
            cell.setCellStyle(title_two_style);
        }

        for (String titleName : c_titleNames) {
            cell = row.createCell(titleCellIndex++);
            cell.setCellValue(titleName);
            cell.setCellStyle(title_two_style);
        }

        FileOutputStream out = new FileOutputStream(
                "D:\\tmp\\xlsx\\" + FooUtils.generateUUID() + ".xlsx");
        wb.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        wb.dispose();

    }

    /**
     * Http Mime Mapping, for ms office documents export.
     */
    @Test
    public void mimeTypeMapping() {
        Map<String, Object> map = Maps.newHashMap();
        map.put(".doc", "application/msword");
        map.put(".docx",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        map.put(".xls", "application/vnd.ms-excel");
        map.put(".xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // more mime types:See:
        // http://blogs.msdn.com/b/vsofficedeveloper/archive/2008/05/08/office-2007-open-xml-mime-types.aspx
    }

    /**
     * 读取excel中的图片，仅支持xls，需要源文件：{{360云盘}}/xlsx/read-images-test-v1.0.xlx
     * <p>
     * Also see:/enjoy-buying/src/main/java/com/feiynn/groupbuying/action/SupplierSysProductTypeAction.java
     */
    @Test
    public void readExcelImages() throws IOException {
        String coverImageName;
        int iterateIndex = 0;
        List<String> realImgNames = Lists.newArrayList();
        String realPath = "/Users/Steve/tmp/xls";

        HSSFWorkbook wb = new HSSFWorkbook(
                new FileInputStream("/Users/Steve/Desktop/test.xls"));
        HSSFSheet dataSheet = wb.getSheetAt(0);

        for (HSSFPictureData tHssfPictureData : wb.getAllPictures()) {
            coverImageName = FooUtils.generateUUID()
                    + (tHssfPictureData.suggestFileExtension().equals("")
                    ? ".png"
                    : "." + tHssfPictureData.suggestFileExtension());
            FileUtils.writeByteArrayToFile(new File(realPath, coverImageName),
                    tHssfPictureData.getData());
            realImgNames.add(coverImageName);
            logger.info("writing image of:{} to realPath:{}", coverImageName,
                    realPath);
            iterateIndex++;
        }

        Map<String, String> rowIndexToImageNameMap = Maps.newHashMap();
        HSSFPicture hssfPicture;
        iterateIndex = 0;
        for (HSSFShape shape : dataSheet.getDrawingPatriarch().getChildren()) {
            HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
            if (shape instanceof HSSFPicture) {

                hssfPicture = (HSSFPicture) shape;
                String tmpImageId = realImgNames.get(hssfPicture.getPictureIndex() - 1);

                logger.info("row1: {} and col1: {} and pictureId: {}", anchor.getRow1(), anchor.getCol1(), tmpImageId);

//                rowIndexToImageNameMap.put(
//                        anchor.getRow1() - (dataStartRowNum + 1) + "",
//                        realImgNames.get(hssfPicture.getPictureIndex() - 1));
            }
        }
        logger.info("rowIndexToImageNameMap is:{},iterateIndex is:{}",
                rowIndexToImageNameMap, iterateIndex);

    }

    @Test
    public void generateExcelWithPicSaved() throws Exception {
        InputStream inp = new FileInputStream(
                "C:\\Users\\think\\Desktop\\产品型号导入模板.xls");
        HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(inp);

        List<HSSFPictureData> pictures = workbook.getAllPictures();
        HSSFSheet sheet = workbook.getSheetAt(0);

        for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
            HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();

            if (shape instanceof HSSFPicture) {
                HSSFPicture pic = (HSSFPicture) shape;
                int row = anchor.getRow1();
                // System.out.println(
                // i + "--->" + anchor.getRow1() + ":" + anchor.getCol1());
                int pictureIndex = pic.getPictureIndex() - 1;
                HSSFPictureData picData = pictures.get(pictureIndex);

                logger.info("anchor row is:{} and col is:{} PictureIndex is:{}",
                        anchor.getRow1(), anchor.getCol1(), pictureIndex);

                savePic(row, picData);
            }
        }
    }

    private void savePic(int i, PictureData pic) throws Exception {
        byte[] data = pic.getData();
        FileUtils.writeByteArrayToFile(
                new File("C:\\Users\\think\\Desktop\\tmp", i + ".jpg"), data);
    }

}
