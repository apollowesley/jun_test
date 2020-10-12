package com.leo.vhr.service;

import com.leo.vhr.mapper.EmployeeMapper;
import com.leo.vhr.model.Employee;
import com.leo.vhr.model.RespPageBean;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/24
 * @version: 1.0
 */
@Service
public class EmployeeService
{
    public static final Logger log= LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;

    SimpleDateFormat yearsdf=new SimpleDateFormat("yyyy");
    SimpleDateFormat monthsdf=new SimpleDateFormat("MM");
    DecimalFormat decimalFormat=new DecimalFormat("##.00");

    public RespPageBean getEmpByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope)
    {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> data=employeeMapper.getEmpByPage(page,size, employee,beginDateScope);
        Long total=employeeMapper.getTotal(employee,beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee)
    {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        Double month=(Double.parseDouble(yearsdf.format(endContract))- Double.parseDouble(yearsdf.format(beginContract)))*12+(Double.parseDouble(monthsdf.format(endContract))-Double.parseDouble(monthsdf.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));

        int result = employeeMapper.insertSelective(employee);
        if(result==1){
            Employee emp=employeeMapper.getEmpById(employee.getId());
            //log.info(emp.toString());
            rabbitTemplate.convertAndSend("leo.mail.welcome",emp);
        }
        return result;
    }

    public Integer maxWorkId()
    {
        return employeeMapper.maxWorkId();
    }

    public Integer delEmpById(Integer id)
    {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee)
    {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list)
    {
        return employeeMapper.addEmps(list);
    }

    public RespPageBean getEmpByPageWithSal(Integer page, Integer size)
    {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> list= employeeMapper.getEmpByPageWithSal(page,size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null,null));
        return respPageBean;
    }

    public Integer updateEmployeeById(@Param("eid") Integer eid,
                                      @Param("sid") Integer sid)
    {
        return employeeMapper.updateEmployeeById(eid,sid);
    }
}
