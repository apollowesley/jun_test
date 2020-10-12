package org.pan.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by xiaopan on 2016-03-03.
 */
public class ExcelExportor {

    final static Logger LOGGER = LoggerFactory.getLogger(ExcelExportor.class);

    public static void export(String filePath, List<String> headerNames, List<String[]> rows) throws IOException {
        LOGGER.debug("准备导到数据到excel:{}",filePath);
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            throw new IOException("文件己存在");
        }

        Path file = Files.createFile(path);
        try(OutputStream outputStream = Files.newOutputStream(file)){
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();

            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headerNames.size(); i++) {
                row.createCell(i).setCellValue(headerNames.get(i));
            }

            for (int i = 0; i < rows.size(); i++) {
                HSSFRow row1 = sheet.createRow(i + 1);
                String[] strings = rows.get(i);
                for (int j = 0; j < strings.length; j++) {
                    row1.createCell(j).setCellValue(strings[j]);
                }
            }
            workbook.write(outputStream);
            LOGGER.info("导出数据到excel成功,文件路径:{}",filePath);
        }catch (Exception e){
            LOGGER.error("导出excel时发生错误",e);
        }
    }

}
