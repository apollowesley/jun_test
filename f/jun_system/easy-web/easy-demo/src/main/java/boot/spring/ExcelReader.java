package boot.spring;

import boot.spring.po.TestVo;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        long startTime = System.currentTimeMillis();

        List<Map<String, Object>> errorList = Lists.newArrayList();
        InputStream inputStream = new FileInputStream("sxssf.xlsx");
        ExcelKit.$Import(TestVo.class).readXlsx(inputStream, new ExcelReadHandler<TestVo>() {

            @Override
            public void onSuccess(int sheetIndex, int rowIndex, TestVo entity) {
                //successList.add(entity); // 单行读取成功，加入入库队列。
                System.out.println(entity.getName());
            }

            @Override
            public void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields) {
                System.out.println(sheetIndex + "行发生了错误！！！");
            }
        });

        long endTime = System.currentTimeMillis();
        System.out.println("读取完成，花费了时间：" + (endTime - startTime) + "毫秒");
    }
}
