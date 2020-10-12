package elastic.job.demo.dataflow;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * 流测试
 *
 * @author demo
 * @version 1.0.0  createTime: 2020/4/3 17:22
 */
public class MyDataFlowJobTest {

    public static void main(String[] args) {

        //三步定义 core  type  lite
        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                new ZookeeperConfiguration("localhost:2181", "MyDataFlowJobTest"));

        registryCenter.init();

        // 定义作业核心配置
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder("MyDataFlowJobTest",
                "0/3 * * ? * *", 1).
                failover(Boolean.TRUE).
                build();

        // 定义DataFlow类型配置 type
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(coreConfiguration,
                MyDataFlowJob.class.getCanonicalName(), Boolean.TRUE);

        // 定义Lite作业根配置 overwrite需要指定，否则修改不生效
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).
                overwrite(Boolean.TRUE).build();

        //构建job
        new JobScheduler(registryCenter, simpleJobRootConfig).init();

    }


}
