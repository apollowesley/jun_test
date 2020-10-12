package elastic.job.demo.simple;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 测试
 *
 * @author demo
 * @version 1.0.0  createTime: 2020/4/3 14:02
 */
public class MyJobTest {

    public static void main(String[] args) {

        //三步定义 core  type  lite
        // TODO 如果修改了代码，跑之前清空ZK

        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                new ZookeeperConfiguration("localhost:2181","MYJobTest"));

        registryCenter.init();

        // 定义作业核心配置
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder("MySimpleJob",
                "0/10 * * ? * *",4).shardingItemParameters("0=RDP, 1=CORE, 2=SIMS, 3=ECIF").
                failover(Boolean.TRUE).
                build();

        // 定义SIMPLE类型配置 type
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(coreConfiguration,
                MySimpleJob.class.getCanonicalName());

        // 作业分片策略
        // 基于平均分配算法的分片策略
        String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();

        // 定义Lite作业根配置 overwrite需要指定，否则修改不生效
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).
                overwrite(Boolean.TRUE).build();

        // LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).
        // jobShardingStrategyClass(jobShardingStrategyClass).overwrite(Bolean.TRUE).build();


        //数据库操作
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/elastic_job_log");
        basicDataSource.setUsername("");
        basicDataSource.setPassword("");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        JobEventConfiguration jobEventConfiguration = new JobEventRdbConfiguration(basicDataSource);


        //构建job
        //new JobScheduler(registryCenter,simpleJobRootConfig).init();

        new JobScheduler(registryCenter,simpleJobRootConfig,jobEventConfiguration).init();
    }
}
