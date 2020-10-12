package com.leo.vhr.controller.system.basic;

import com.leo.vhr.model.Department;
import com.leo.vhr.model.RespBean;
import com.leo.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/18
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController
{
    @Autowired
    DepartmentService departmentService;

    /**
     * @param null
     * @return
     * @description：递归查询
     * @since v1.0.0
     * author Leo
     * date 2020/2/18
     */
    @GetMapping("/")
    public List<Department> getAllDep()
    {
        return departmentService.getAllDepByParentId();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep)
    {
        departmentService.addDep(dep);
        if (dep.getResult() == 1)
        {
            return RespBean.ok("添加成功！", dep);
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id)
    {
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2)
        {
            return RespBean.error("该部门下有子部门，删除失败！");
        }
        else if (dep.getResult() == -1)
        {
            return RespBean.error("该部门下有员工");
        }
        else if (dep.getResult() == 1)
        {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
