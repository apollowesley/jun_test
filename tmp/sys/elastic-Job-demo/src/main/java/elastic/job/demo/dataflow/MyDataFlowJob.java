package elastic.job.demo.dataflow;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 流数据
 *
 * @author demo
 * @version 1.0.0  createTime: 2020/4/3 17:17
 */
public class MyDataFlowJob implements DataflowJob<String> {


    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("开始获取数据");
        Random random = new Random();
        if (random.nextInt() % 3 != 0) {
            return null;
        }
        return Arrays.asList("张三", "李四", "王五");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {

        for (String s : list) {
            //处理完就要清掉数据，否则会一直运行
            System.out.println("当前时间  " + new SimpleDateFormat("HH:mm:ss").format(new Date()) +"读取数据 "+s);
        }

    }
}
