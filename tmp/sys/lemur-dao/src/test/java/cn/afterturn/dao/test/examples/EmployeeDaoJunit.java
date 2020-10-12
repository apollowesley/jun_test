package cn.afterturn.dao.test.examples;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.util.ReflectionTestUtils;

import cn.afterturn.dao.pager.IDaoPager;
import cn.afterturn.dao.test.examples.dao.EmployeeDao;
import cn.afterturn.dao.test.examples.entity.Employee;

/**
 * 单元测试
 * 
 * @author yanping.shi
 * 
 */
public class EmployeeDaoJunit extends SpringTxTestCase {

    protected final Logger logger = LoggerFactory.getLogger(EmployeeDaoJunit.class);

    @Resource(name = "employeeDao")
    private EmployeeDao    employeeDao;

    int                    maxNum;

    @Before
    public void testGetCount() {
        maxNum = employeeDao.getCount();
        logger.info("当前数据条数 --------------------------(" + maxNum + ")");
    }

    @Test
    public void testGetMap() {
        logger
            .info("--------testGetMap--------------------------------------------------------------");

        // 如果没有数据获取报错
        Map<String, Object> mp = employeeDao.getMap("001", "张开忠");
        logger.info(String.valueOf(mp.get("id")));
        logger.info(String.valueOf(mp.get("name")));
        logger.info(String.valueOf(mp.get("empno")));
        logger.info(String.valueOf(mp.get("age")));
        logger.info(String.valueOf(mp.get("birthday")));
        logger.info(String.valueOf(mp.get("salary")));
    }

   // @Test
    public void testGetEntity() {
        Employee employee = employeeDao.getEmployee("001");
        logger.info("testGetEntity --" + employee.getName());
    }

   @Test
    public void testListAll() {
        logger
            .info("--------testListAll--------------------------------------------------------------");
        Employee employee = new Employee();
        employee.setName("scott");
        employee.setAge(20);
        List<Employee> list = employeeDao.getAllEmployees(employee, 0, 10);

        for (Employee mp : list) {
            logger.info(mp.getId());
            logger.info(mp.getName());
            logger.info(mp.getEmpno());
            logger.info(mp.getAge().toString());
            logger.info(mp.getBirthday().toString());
            logger.info(String.valueOf(mp.getSalary()));
        }

    }

}
