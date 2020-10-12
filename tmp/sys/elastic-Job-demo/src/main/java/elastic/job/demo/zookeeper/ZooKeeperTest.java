package elastic.job.demo.zookeeper;

import org.I0Itec.zkclient.ZkClient;

/**
 * zk集群连接性测试
 *
 * @author demo
 * @version 1.0.0  createTime: 2020/4/3 16:46
 */
public class ZooKeeperTest {

    /**
     * 测试zk的连通性
     */
    public static void main(String[] args) {

        //服务端配置
        ZkClient zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183");

        //node 节点
        String node = "/app1";
        if (!zkClient.exists(node)) {
            //创建实例
            zkClient.createPersistent(node, "hello zk");
        }

        System.out.println("zk 读取数据:" + zkClient.readData(node));

    }

}
