package cn.kiwipeach.oracle.test;

import cn.kiwipeach.bean.Employ;
import cn.kiwipeach.util.DBTools;
import oracle.sql.ROWID;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by liuburu on 2017/6/6.
 */
public class OracleUtilTest {

    /**
     * 初始化数据源
     */
    @Before
    public void beforeTest() {
        //默认将使用datasource.properties配置文件
        DBTools.initDataSource(null);
    }

    @Test
    public void testQueryByPrimaryKey() {
        String sql = "SELECT * FROM emp WHERE empno = ?";
        Employ employ = DBTools.queryByPrimaryKey(Employ.class, sql, "7777");
        System.out.println(employ);
        /**
         console result:
         Employ{empno=7777, ename='卡卡罗特', job='程序员', mgr=7902, hireDate=Wed Dec 17 00:00:00 CST 1980, sal=0.0, comm=0.0, deptno=20}
         */
    }

    @Test
    public void testQuery() {
        String sql = "SELECT * FROM emp";
        List<Employ> employs = DBTools.queryList(Employ.class, sql);
        for (Employ employ : employs) {
            System.out.println(employ);
        }
        /**
         * console result:
         Employ{empno=7698, ename='BLAKE', job='MANAGER', mgr=7839, hireDate=Fri May 01 00:00:00 CST 1981, sal=2850.0, comm=0.0, deptno=30}
         Employ{empno=7782, ename='CLARK', job='MANAGER', mgr=7839, hireDate=Tue Jun 09 00:00:00 CST 1981, sal=2450.0, comm=0.0, deptno=10}
         Employ{empno=7839, ename='KING', job='PRESIDENT', mgr=0, hireDate=Tue Nov 17 00:00:00 CST 1981, sal=5000.0, comm=0.0, deptno=10}
         Employ{empno=7844, ename='TURNER', job='SALESMAN', mgr=7698, hireDate=Tue Sep 08 00:00:00 CST 1981, sal=1500.0, comm=0.0, deptno=30}
         Employ{empno=7900, ename='JAMES', job='CLERK', mgr=7698, hireDate=Thu Dec 03 00:00:00 CST 1981, sal=950.0, comm=0.0, deptno=30}
         Employ{empno=7902, ename='FORD', job='ANALYST', mgr=7566, hireDate=Thu Dec 03 00:00:00 CST 1981, sal=3000.0, comm=0.0, deptno=20}
         Employ{empno=7934, ename='MILLER', job='CLERK', mgr=7782, hireDate=Sat Jan 23 00:00:00 CST 1982, sal=1300.0, comm=0.0, deptno=10}
         */
    }


    @Test
    public void testInsert() throws ParseException {
        Connection connection = DBTools.getConnection();
        String sql = "INSERT INTO emp(empno,ename,job,mgr,sal,hiredate,comm,deptno) VALUES(?,?,?,?,?,?,?,?)";
        java.util.Date myBirthday = new SimpleDateFormat("yyyy/MM/dd").parse("1994/11/12");
        Object[] paramArgs = new Object[]{4444, "猕猴桃", "SALESMAN", 7902, 5000, new java.sql.Date(myBirthday.getTime()), 1600.00, 20};
        int insertRow = DBTools.update(connection, sql, paramArgs);
        System.out.println(insertRow > 0 ? "插入成功!" : "插入失败");
        /**
         console result:
         插入成功!
         */
    }

    @Test
    public void testUpdate() {
        String sql = "UPDATE emp SET sal=? WHERE empno = ?";
        Connection connection = DBTools.getConnection();
        Object[] paramArgs = new Object[]{"4000", 4444};
        int insertRow = DBTools.update(connection, sql, paramArgs);
        System.out.println(insertRow > 0 ? "更新成功!" : "更新失败");
        /**
         console result:
         更新成功!
         */
    }

    @Test
    public void testRemove() {
        String sql = "DELETE FROM emp WHERE empno = ?";
        Connection connection = DBTools.getConnection();
        Object[] paramArgs = new Object[]{4444};
        int insertRow = DBTools.update(connection, sql, paramArgs);
        System.out.println(insertRow > 0 ? "删除成功!" : "删除失败");
        /**
         console result:
         删除成功!
         */
    }

    /**
     * 获取统计值或者字段值
     */
    @Test
    public void testGetValue() {
        String sql = "SELECT COUNT(1) FROM emp WHERE deptno = ?";
        Object result = DBTools.getValue(sql, 20);
        System.out.println("部门deptno=20的人员有?   " + result + "个");
    }

    /**
     * 插入同时获取主键值
     */
    @Test
    public void testGetPrimaryKey() throws ParseException {
        String sql = "INSERT INTO emp(empno,ename,job,mgr,sal,hiredate,comm,deptno) VALUES(?,?,?,?,?,?,?,?)";
        java.util.Date myBirthday = new SimpleDateFormat("yyyy/MM/dd").parse("1994/11/12");
        Object[] paramArgs = new Object[]{4444, "猕猴桃", "SALESMAN", 7902, 5000, new java.sql.Date(myBirthday.getTime()), 1600.00, 20};
        Connection connection = DBTools.getConnection();
        ROWID keyValue = (ROWID) DBTools.insert(connection, sql, paramArgs);
        System.out.println("返回主键值:" + keyValue.stringValue());
        /**
         返回主键值:AAAWCWAARAAAACWAAB(与oracle的rowid一致)
         */
    }


    @Test
    public void testTransaction() {
        String sql1 = "INSERT INTO emp(empno,ename,job) VALUES(?,?,?)";
        String sql2 = "INSERT INTO emp(empno,ename,job) VALUES(?,?,?)";
        Connection connection = DBTools.getConnection();
        DBTools.beginTransaction(connection);
        int resultRow1 = DBTools.update(connection, sql1, 1001, "xiaoming", "SALESMAN");
        int resultRow2 = DBTools.update(connection, sql2, 1002, "xiaohong", "SALESMAN");
        System.out.println("insertRow1:" + resultRow1);
        System.out.println("insertRow2:" + resultRow2);
        DBTools.rollback(connection);//回滚事务
        //DBTools.commit(connection);//提交事务
    }

    /**
     * 批量插入数据操作
     *
     * @throws SQLException
     */
    @Test
    public void batchInsert() throws SQLException {
        long beginTime = System.currentTimeMillis();
        Connection connection = DBTools.getConnection();
        String sql = "insert into emp(empno,ename,job) values(?,?,?)";
        DBTools.beginTransaction(connection);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //批量插入9万条数据
            for (int i = 10000; i < 100000; i++) {
                preparedStatement.setObject(1, i);
                preparedStatement.setObject(2, "u"+i);
                preparedStatement.setObject(3, "r"+i);
                preparedStatement.addBatch();
                if (i % 100 == 0) {
                    System.out.println("已经执行到:"+i);
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
            DBTools.commit(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            DBTools.rollback(connection);
        }
        connection.close();
        long endTime = System.currentTimeMillis();
        System.out.println("耗时:"+(endTime-beginTime)/1000+"秒");
        /**
         console result:
         耗时:3秒
         */
    }

}
