package com.leo.vhr.mapper;

import com.leo.vhr.model.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmpByPage(@Param("page") Integer page,
                                @Param("size") Integer size,
                                @Param("emp") Employee employee,
                                @Param("beginDateScope") Date[] beginDateScope);

    Long getTotal(@Param("emp") Employee employee,
                  @Param("beginDateScope") Date[] beginDateScope);

    Integer maxWorkId();


    Integer addEmps(List<Employee> list);

    Employee getEmpById(Integer id);

    List<Employee> getEmpByPageWithSal(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeById(Integer eid, Integer sid);
}