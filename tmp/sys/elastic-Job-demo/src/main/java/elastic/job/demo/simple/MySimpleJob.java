package elastic.job.demo.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简单实现，未经任何封装的类型。需实现SimpleJob 接口
 *
 * @author demo
 * @version 1.0.0  createTime: 2020/4/3 11:29
 */
@Slf4j
public class MySimpleJob implements SimpleJob {


    @Override
    public void execute(ShardingContext context) {

     log.info("分片项:{},线程id:{},当前时间:{},分片参数:{}",context.getShardingItem(),
                Thread.currentThread().getId(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                context.getShardingParameter());

    }
}
