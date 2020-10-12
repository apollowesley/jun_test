package elastic.job.demo.script;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * 脚本任务
 *
 * @author demo
 * @version 1.0.0  createTime: 2020/4/3 17:54
 */
public class ScriptJobTest {

    public static void main(String[] args) {
        // ZK注册中心
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181",
                "ScriptJobTest"));
        regCenter.init();

        // 定义作业核心配置
        JobCoreConfiguration scriptJobCoreConfig = JobCoreConfiguration.newBuilder("MyScriptJob",
                "0/5 * * * * ?", 1).build();
        // 定义SCRIPT类型配置
        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(scriptJobCoreConfig,
                "D:/software/test.bat");

        // 定义Lite作业根配置
        LiteJobConfiguration scriptJobRootConfig = LiteJobConfiguration.newBuilder(scriptJobConfig).build();

        // 构建Job
        new JobScheduler(regCenter, scriptJobRootConfig).init();
    }
}
