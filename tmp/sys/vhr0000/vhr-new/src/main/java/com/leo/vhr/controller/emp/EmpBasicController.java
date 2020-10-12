package com.leo.vhr.controller.emp;

import com.leo.vhr.model.*;
import com.leo.vhr.service.*;
import com.leo.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/24
 * @version: 1.0
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController
{
    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;
    @GetMapping("/")
    public RespPageBean getEmpByPage(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     Employee employee,
                                     Date[] beginDateScope){
//        System.out.println(employee);
//        System.out.println(Arrays.toString(beginDateScope));
        return employeeService.getEmpByPage(page,size,employee,beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if(employeeService.addEmp(employee)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean delEmpById(@PathVariable Integer id){
        if(employeeService.delEmpById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateEmp(employee)==1){
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @GetMapping("/maxWorkID")
    public RespBean maxWorkId(){
        RespBean respBean= RespBean.build().setStatus(200)
                .setObj(String.format("%08d",employeeService.maxWorkId()+1));
        return respBean;
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepByParentId();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> list= (List<Employee>) employeeService.getEmpByPage(null,null,null,null).getData();
        //System.out.println(list.toString());
        return POIUtils.employeeExcel(list);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException
    {
        List<Employee> list=POIUtils.excelEmployeeImport(file,nationService.getAllNations(), politicsstatusService.getAllPoliticsstatus(),departmentService.getAllDepartmentsWithOutChildren(), positionService.getAllPositions(),jobLevelService.getAllJobLevels());
        String s = employeeService.addEmps(list).toString();
        System.out.println("sssssss====>"+s);
        if(employeeService.addEmps(list)==1){
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }
}
