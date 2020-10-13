package boot.spring.controller;

import boot.spring.po.TestVo;
import boot.spring.service.TestService;
import boot.spring.service.impl.DataConsumer;
import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Api(tags = "excel接口")
@Controller
public class ExcelController {

    @Autowired
    private TestService testService;

    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

    /**
     * 导入数据测试
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/importTest", method = RequestMethod.POST)
    public ResponseEntity<?> importTest(@RequestParam MultipartFile file) throws IOException {
        long beginMillis = System.currentTimeMillis();
        log.debug("开始处理excel文件");
        BlockingQueue<TestVo> queue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                20,
                1000,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new NameTreadFactory(),
                new MyIgnorePolicy()
        );
        executor.prestartAllCoreThreads(); // 预启动所有核心线程

        executor.execute(new DataConsumer(queue, testService));

        Set<String> testSet = testService.querySet();

        ExcelUtil.read07BySax(file.getInputStream(), 2, createRowHandler());

        //ExcelSaxReadrss abc = new ExcelSaxReadrss();

//        ExcelKit.$Import(TestVo.class).readXlsx(file.getInputStream(), new ExcelReadHandler<TestVo>() {
//            @Override
//            public void onSuccess(int sheetIndex, int rowIndex, TestVo entity) {
//                try {
//                    if (!testSet.contains(entity.getUid())) {
//                        queue.put(entity);
//                        testSet.add(entity.getUid());
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //executor.execute(new DataProducer(queue, entity));
//            }
//
//            @Override
//            public void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields) {
//                System.err.println("导入出错：" + rowIndex);
//            }
//        });
        return ResponseEntity.ok("导入完成，花费了时间：" + (System.currentTimeMillis() - beginMillis) / 1000l + "秒");
    }

    private static RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
            }
        };
    }

    public static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println("新创建了线程：" + t.getName());
            return t;
        }
    }

    public static class MyIgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            System.err.println(r.toString() + " 被拒绝!");
        }
    }
}
