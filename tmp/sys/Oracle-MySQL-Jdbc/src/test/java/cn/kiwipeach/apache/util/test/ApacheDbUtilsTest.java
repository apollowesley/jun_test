/*
 * Copyright 2018 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.apache.util.test;

import cn.kiwipeach.bean.Employ;
import cn.kiwipeach.rshandler.KeyedPlusHandler;
import cn.kiwipeach.util.DBTools;
import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Create Date: 2018/04/18
 * Description: apache提供的工具类测试
 * 官网测试案例
 * http://commons.apache.org/proper/commons-dbutils/examples.html
 * <p>
 * 优秀的个人改造博客
 * 工具类改造:http://minjiechenjava.iteye.com/blog/2205789
 * 测试案例齐全:https://www.cnblogs.com/xdp-gacl/p/4007225.html
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class ApacheDbUtilsTest {

    private DataSource dataSource;
    private Connection connection;

    @Before
    public void init() {
        DBTools.initDataSource(null);
        connection = DBTools.getConnection();
        dataSource = DBTools.getDataSource();
    }

    /**
     * 测试1:基础用法
     */
    ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
        public Object[] handle(ResultSet rs) throws SQLException {
            if (!rs.next()) {
                return null;
            }
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            Object[] result = new Object[cols];
            for (int i = 0; i < cols; i++) {
                result[i] = rs.getObject(i + 1);
            }
            return result;
        }
    };

    @Test
    public void testBasicUse() throws SQLException {
        //1.1使用数据源dataSource
        //QueryRunner run = new QueryRunner(dataSource);
        //String sql = "select * from emp where empno = ?";
        //Object[] result = run.query(sql, h, "7777");

        //1.2使用数据源连接connection
        String sql = "select * from emp where empno = ?";
        Object[] result = new QueryRunner().query(connection, sql, h, "7777");
        System.out.println(result);
    }

    /**
     * 测试2: 删除、更新操作
     */
    @Test
    public void testUpdateInsert() throws SQLException {
        QueryRunner run = new QueryRunner(dataSource);
        int insertRow = run.update(
                "INSERT INTO emp (empno,ename,sal) VALUES (?,?,?)",
                1002, "John Doe", 1.82);
        System.out.println("插入结果:" + insertRow);
        int updateRow = run.update(
                "UPDATE emp SET ename = ? where empno = ?",
                "孙悟空", 1002);
        System.out.println("更新结果:" + updateRow);
    }

    /**
     * 测试3:AsyncQueryRunner 异步接口,对于耗时较长的sql可以使用下面的方法执行
     */
    @Test
    public void testAsyncQueryRunner() {
        Future<Integer> future = null;
        try {
            String updateSql = "UPDATE emp SET ename=? WHERE empno=?";
            //3.1使用数据源
            //QueryRunner queryRunner = new QueryRunner(dataSource);
            //AsyncQueryRunner asyncQueryRunner = new AsyncQueryRunner(Executors.newCachedThreadPool(), queryRunner);
            //future = asyncQueryRunner.update(updateSql, "美猴王", 1002);

            //3.1 使用connection
            AsyncQueryRunner asyncQueryRunner = new AsyncQueryRunner(Executors.newCachedThreadPool());
            future = asyncQueryRunner.update(connection, updateSql, "卡卡罗特", 1002);

            //设置超时时间，如果1000 milliseconds 时间内还未执行完成，则会抛出异常
            Integer updatedRecords = future.get(500, TimeUnit.MILLISECONDS);
            System.out.println(updatedRecords + " record(s) updated.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * 测试4：ResultSetHandler有arrays,maps,javabean实现，能够轻易的提供结果集映射；而不会想测试一样返回笨重的object[]
     * （原文）In recognition of this DbUtils provides a set of ResultSetHandler implementations in the
     * org.apache.commons.dbutils.handlers package that perform common transformations into arrays, Maps, and JavaBeans.
     * <p>
     * ArrayHandler：把结果集中的第一行数据转成对象数组。
     * ArrayListHandler：把结果集中的每一行数据都转成一个数组，再存放到List中。
     * BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
     * BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。
     * ColumnListHandler：将结果集中某一列的数据存放到List中。
     * KeyedHandler(name)：将结果集中的每一行数据都封装到一个Map里，再把这些map再存到一个map里，其key为指定的key。
     * MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
     * MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List
     */
    @Test
    public void testBeanHandler() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String querySql = "SELECT * FROM emp WHERE ename = ?";
        //4.1 测试BeanHandler
        ResultSetHandler<Employ> beanHandler = new BeanHandler(Employ.class);
        Employ employ = queryRunner.query(querySql, beanHandler, "ALLEN");
        System.out.println(employ);

        //4.2 测试BeanListHandler
        ResultSetHandler<List<Employ>> beanListHandler = new BeanListHandler<Employ>(Employ.class);
        List<Employ> employList = queryRunner.query(querySql, beanListHandler, "卡卡罗特");
        for (Employ e : employList) {
            System.out.println(e);
        }

        //4.3 测试ArrayHandler,返回一个查询结果，如果有多个，那么返回第一个查询结果
        ResultSetHandler<Object[]> arrayHandler = new ArrayHandler();
        Object[] objects = queryRunner.query(querySql, arrayHandler, "卡卡罗特");
        System.out.println(objects);//object[0,1,2...n]存放的是实体字段

        //4.4 测试 ArrayListHandler
        ResultSetHandler<List<Object[]>> arrayListHandler = new ArrayListHandler();
        List<Object[]> arrayListResult = queryRunner.query(querySql, arrayListHandler, "卡卡罗特");
        System.out.println(arrayListResult);//存放多个实体字段的List集合

        //4.5 测试ColumnListHandler,某一列的字段集合存放在查询结果中
        ColumnListHandler<Employ> columnListHandler = new ColumnListHandler<Employ>("ename");
        List<Employ> columnListResult = queryRunner.query(querySql, columnListHandler, "卡卡罗特");
        System.out.println(columnListResult);

        //4.6 测试KeyedHandler,是一个hashmap，key相同则会覆盖;key为指定列，value为查询返回的obect类型
        KeyedHandler<String> keyedHandler = new KeyedHandler<String>("empno");
        Map<String, Map<String, Object>> keyHandleResult = queryRunner.query(querySql, keyedHandler, "卡卡罗特");
        System.out.println(keyHandleResult);

        //4.7 测试MapHandler，直返会结果集的第一行数据
        MapHandler mapHandler = new MapHandler();
        Map<String, Object> mapHandleResult = queryRunner.query(querySql, mapHandler, "卡卡罗特");
        System.out.println(mapHandleResult);

        //4.8 测试MapListHandler，结果集中的每一行都会返回到map中去
        MapListHandler mapListHandler = new MapListHandler();
        List<Map<String, Object>> mapListResult = queryRunner.query(querySql, mapListHandler, "卡卡罗特");
        System.out.println(mapListResult);

    }

    /**
     * 测试5：重写KeyedHandler类，具体见KeyedPlusHandler
     */
    @Test
    public void testRowProcessor() throws SQLException {
        /**
         * 背景:
         * 4.8 案例中的 Map<String, Map<String, Object>> result = queryRunner.query(querySql, keyedHandler, "卡卡罗特");
         * 个人觉得返回结果对象为Object对开发来说不是很方便，想使用泛型来进行改造。使其返回Map<key,Record>
         */
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String querySql = "SELECT * FROM emp WHERE ename = ?";
        KeyedPlusHandler<String, Employ> keyedPlusHandler = new KeyedPlusHandler<String, Employ>(Employ.class);
        Map<String, Employ> keyPlusHandleResult = queryRunner.query(querySql, keyedPlusHandler, "卡卡罗特");
        System.out.println(keyPlusHandleResult);
    }

    /**
     * 测试6：
     */


}
