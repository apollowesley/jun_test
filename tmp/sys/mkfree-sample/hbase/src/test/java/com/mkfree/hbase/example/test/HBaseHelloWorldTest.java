package com.mkfree.hbase.example.test;

import com.mkfree.hbase.example.HBaseHelloWorld;
import com.mkfree.hbase.example.HashChoreWoker;
import com.mkfree.hbase.example.domain.HBaseDomain;
import com.mkfree.hbase.example.domain.HBaseOrder;
import com.mkfree.hbase.example.repository.jpa.OrderRepository;
import com.mkfree.hbase.example.domain.Order;
import com.mkfree.hbase.example.repository.solr.SolrOrder;
import com.mkfree.hbase.example.repository.solr.SolrOrderRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.math.BigInteger;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HBaseHelloWorld.class)
public class HBaseHelloWorldTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Connection connection = null;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SolrOrderRepository solrOrderRepository;

    @Before
    public void init() throws IOException {
        Configuration config = HBaseConfiguration.create();
        String zkAddress = "hb-proxy-pub-wz95d8474e452itz6-master1-001.hbase.rds.aliyuncs.com:2181,hb-proxy-pub-wz95d8474e452itz6-master2-001.hbase.rds.aliyuncs.com:2181,hb-proxy-pub-wz95d8474e452itz6-master3-001.hbase.rds.aliyuncs.com:2181";
        config.set(HConstants.ZOOKEEPER_QUORUM, zkAddress);
        connection = ConnectionFactory.createConnection(config);
    }


    @Test
    public void createTable() throws IOException {

        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf("t_canteen_order"));
        tableDescriptorBuilder.setColumnFamily(ColumnFamilyDescriptorBuilder.of("b"));


        tableDescriptorBuilder.setReplicationScope(1);
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
        Admin admin = connection.getAdmin();
        admin.createTable(tableDescriptor);

    }

    @Test
    public void deleteTable() throws IOException {
        connection.getAdmin().disableTable(TableName.valueOf("t_order"));
        connection.getAdmin().deleteTable(TableName.valueOf("t_order"));
    }

    @Test
    public void putData() throws IOException {
        Table table = connection.getTable(TableName.valueOf("t_order"));

        Put put = new Put("485996107908864282".getBytes());
        put.addColumn("b".getBytes(), "ID".getBytes(), "282468809701699584".getBytes());
        put.addColumn("b".getBytes(), "customerId".getBytes(), "18754".getBytes());
        put.addColumn("b".getBytes(), "customerMobile".getBytes(), "13794638459".getBytes());
        put.addColumn("b".getBytes(), "payTotalPrice".getBytes(), Bytes.toBytes(6.95D));
        table.put(put);

        table.close();
    }


    long reverse(long v) {
        long ret = 0;
        for (long x = v; x != 0; x /= 10)
            ret = ret * 10 + x % 10;
        return ret;
    }
    @Test
    public void putBatchData() {
        Page<Order> page = orderRepository.findAll(PageRequest.of(0, 100000000, new Sort(Sort.Direction.DESC, "id")));
        try {
            Table table = connection.getTable(TableName.valueOf("uat"));
            for (Order order : page.getContent()) {

//                Long id = reverse(order.getOrderNum());

                byte [] rowkey = Bytes.add(MD5Hash.getMD5AsHex(Bytes.toBytes(order.getOrderNum())).substring(0, 8).getBytes(),
                        Bytes.toBytes(order.getOrderNum()));



//                String id2 = DigestUtils.md5Hex(order.getOrderNum().toString());
//                String id3 = DigestUtils.md5Hex( StringUtils.reverse(order.getOrderNum().toString()));
                System.out.println("Bytes.toBytes(StringUtils.reverse(order.getOrderNum().toString())).length : "+Bytes.toBytes(StringUtils.reverse(order.getOrderNum().toString())).length);
                System.out.println("rowkey.length : "+rowkey.length);
//                System.out.println(id2);
//                System.out.println(id3);


                Put put = new Put(rowkey);
                put.addColumn(Bytes.toBytes("b"), Bytes.toBytes("ID"), Bytes.toBytes(order.getOrderNum()));
                put.addColumn(Bytes.toBytes("b"), Bytes.toBytes("payTotalPrice"), Bytes.toBytes(order.getPayTotalPrice().doubleValue()));
                put.addColumn(Bytes.toBytes("b"), Bytes.toBytes("customerId"), Bytes.toBytes(order.getCustomerId()));
                put.addColumn(Bytes.toBytes("b"), Bytes.toBytes("customerMobile"), StringUtils.isNotBlank(order.getCustomerMobile()) ? Bytes.toBytes(order.getCustomerMobile()) : "".getBytes());
                put.addColumn(Bytes.toBytes("b"), Bytes.toBytes("orderType"), Bytes.toBytes(order.getOrderType()));
                table.put(put);
                System.out.println("put data success, orderNum : " + order.getOrderNum());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getData() throws IOException, NoSuchFieldException, IllegalAccessException {
        Table table = connection.getTable(TableName.valueOf("t_order"));
        Get get = new Get("485996107908864282".getBytes());
        Result result = table.get(get);
        HBaseOrder hbaseOrder = new HBaseOrder();
        HBaseDomain.orh(hbaseOrder, result);
        log.info("{}", hbaseOrder);


    }

    @Test
    public void solrQuery() throws IOException, SolrServerException, NoSuchFieldException, IllegalAccessException {

        List<SolrOrder> orderList = solrOrderRepository.findByCustomerMobile("13876743009");
        Table table = connection.getTable(TableName.valueOf("uat_t_canteen_order"));

        for (SolrOrder solrOrder : orderList) {
            // 从hbase中获取数据
            Get get = new Get(Bytes.toBytes(solrOrder.getId()));
            Result result = table.get(get);
            HBaseOrder hbaseOrder = new HBaseOrder();
            HBaseDomain.orh(hbaseOrder, result);
            log.info("{}", hbaseOrder);

        }

//        HttpSolrClient solrClient = new HttpSolrClient.Builder("http://hb-proxy-pub-wz95d8474e452itz6-master3-001.hbase.rds.aliyuncs.com:8983/solr").build();
//        SolrQuery solrQuery = new SolrQuery("*:*");
//        QueryResponse response = solrClient.query("t_order_collection", solrQuery);
//        List<SolrOrder> solrOrders =  response.getBeans(SolrOrder.class);
//        solrOrders.forEach(solrOrder -> {
//            System.out.println(solrOrder);
//        });
    }


    @Test
    public void testPartitionAndCreateTable() throws Exception{
        Admin admin = connection.getAdmin();
        admin.disableTable(TableName.valueOf("uat"));
        admin.deleteTable(TableName.valueOf("uat"));


        HashChoreWoker worker = new HashChoreWoker(999999,100);
        byte [][] splitKeys = worker.calcSplitKeys();

        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf("uat"));
        tableDescriptorBuilder.setColumnFamily(ColumnFamilyDescriptorBuilder.of("b"));


        tableDescriptorBuilder.setReplicationScope(1);
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();

        admin.createTable(tableDescriptor,splitKeys);


    }

    @After
    public void dis() throws IOException {
        connection.close();
    }
}
