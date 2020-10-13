package boot.spring;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建测试数据
 */
public class CreateTestExcel {

    public static void main(String[] args) {
//        try {
//            createSXSSFExcel(5);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ExcelSaxReadrss abc = new ExcelSaxReadrss();
//        abc.process();
        //createExcel(1000);
    }

    private static void createSXSSFExcel(int rowsNum) throws IOException {
        long startTime = System.currentTimeMillis();
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        byte[] nbyte = new byte[10];
        Random randomno = new Random();
        Sheet sh = wb.createSheet();

        //创建标题
        Row row = sh.createRow(0);
        String[] titles = new String[]{"编号", "唯一标识", "名称", "数值", "类型", "创建日期"};
        for (int cellnum = 0; cellnum < titles.length; cellnum++) {
            Cell cell = row.createCell(cellnum);
            cell.setCellValue(titles[cellnum]);
        }

        for (int rownum = 1; rownum < rowsNum; rownum++) {
            row = sh.createRow(rownum);
            List<Object> rowData = new ArrayList<>();
            rowData.add(rownum + 1);                            //rowNum
            rowData.add(UUID.randomUUID());                     //uid
            rowData.add("testName" + rownum);                   //name
            rowData.add(randomno.nextDouble());                 //number
            randomno.nextBytes(nbyte);
            rowData.add(nbyte[rownum % 10]);                    //bytes
            rowData.add(DateUtil.date());                       //date

            for (int cellnum = 0; cellnum < rowData.size(); cellnum++) {
                Cell cell = row.createCell(cellnum);
                cell.setCellValue(rowData.get(cellnum).toString());
            }
        }

        //创建结束标示
        Row endRow = sh.createRow(rowsNum);
        Cell cell = endRow.createCell(0);
        cell.setCellValue("-1");

        FileOutputStream out = new FileOutputStream("sxssf" + rowsNum + ".xlsx");
        wb.write(out);
        out.close();
        wb.dispose();
        long endTime = System.currentTimeMillis();
        System.out.println("生成了" + rowsNum + "行，花了时间（毫秒）：" + (endTime - startTime));
    }
}