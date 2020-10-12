package cn.afterturn.dao.test.examples.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.afterturn.dao.annotation.IDaoParam;
import cn.afterturn.dao.annotation.IDaoSql;
import cn.afterturn.dao.test.examples.entity.Employee;

@Repository("employeeDao")
public interface EmployeeDao {

    public List<Employee> getAllEmployees(@IDaoParam("employee") Employee employee,
                                               @IDaoParam("page") int page,
                                               @IDaoParam("rows") int rows);

    Employee getEmployee(String empno);

    Map<String, Object> getMap(@IDaoParam("empno") String empno, @IDaoParam("name") String name);

    @IDaoSql("SELECT count(*) FROM employee")
    Integer getCount();

    int update(@IDaoParam("employee") Employee employee);

    void insert(@IDaoParam("employee") Employee employee);
}
