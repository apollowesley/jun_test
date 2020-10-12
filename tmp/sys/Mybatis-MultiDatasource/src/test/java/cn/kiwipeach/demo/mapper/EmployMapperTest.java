package cn.kiwipeach.demo.mapper;

import cn.kiwipeach.demo.SpringJunitBase;
import cn.kiwipeach.demo.domain.Employ;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2018/01/08
 **/
public class EmployMapperTest extends SpringJunitBase {



    @Test
    public void testDruidDatesource() throws SQLException {
        Connection connection =dataSource.getConnection();
        String sql = "select * from emp where empno = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"7777");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }
        connection.close();
    }

    @Qualifier("mysqlDataSource")
    @Autowired
    private DataSource dataSource;


    @Autowired
    private EmployMapper employMapper;

    @Test
    public void selectByPrimaryKey() throws Exception {
        Employ employ = employMapper.selectByPrimaryKey(new BigDecimal(7777));
        System.out.println(JSON.toJSONString(employ));
    }
    @Test
    public void insertSelective() throws Exception {
        Employ employ = employMapper.selectByPrimaryKey(new BigDecimal(7369));
        employ.setEmpno(new BigDecimal("6666"));
        employ.setEname("测试用户");
        int insertRow = employMapper.insertSelective(employ);
        System.out.println("插入影响记录行数:"+insertRow);
    }



}