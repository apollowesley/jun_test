/*
 * Copyright 2017 KiWiPeach.
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
package cn.kiwipeach.demo.multiple;

import cn.kiwipeach.demo.SpringJunitBase;
import cn.kiwipeach.demo.datasource.CustomMultipleDataSource;
import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.mapper.EmployMapper;
import cn.kiwipeach.demo.mapper.MysqlEmployMapper;
import cn.kiwipeach.demo.mapper.OracleEmployMapper;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Create Date: 2018/01/14
 * Description: 多数据源测试
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class MulityDatasourceTestCase1 extends SpringJunitBase {


    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource mysqlDatasource;

    @Autowired
    @Qualifier("oracleDataSource")
    private DataSource oracleDatasource;

    /**
     * 测试mysql和oracle数据源是否可用
     */
    @Test
    public void testOracleMysqlDatasource() throws SQLException {
        Assert.assertNotNull(mysqlDatasource.getConnection());
        Assert.assertNotNull(oracleDatasource.getConnection());
    }


    @Autowired
    private EmployMapper employMapper;

//    @Autowired
//    private OracleEmployMapper oracleEmployMapper;

//    @Autowired
//    private MysqlEmployMapper mysqlEmployMapper;
    /**
     * 手动设置数据源，查询操作
     */
    @Test
    public void testMulityDatasource1(){

        Employ defaultEmploy = employMapper.selectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(defaultEmploy));

        CustomMultipleDataSource.setDataSourceKey("mysqlDataSource");
        Employ mysqlEmploy = employMapper.selectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(mysqlEmploy));

        CustomMultipleDataSource.setDataSourceKey("oracleDataSource");
        Employ oracleEmploy = employMapper.selectByPrimaryKey(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(oracleEmploy));
    }



}
