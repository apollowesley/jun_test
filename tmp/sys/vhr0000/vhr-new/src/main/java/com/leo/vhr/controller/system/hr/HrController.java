package com.leo.vhr.controller.system.hr;

import com.leo.vhr.model.Hr;
import com.leo.vhr.model.RespBean;
import com.leo.vhr.model.Role;
import com.leo.vhr.service.HRService;
import com.leo.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/20
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/hr")
public class HrController
{
    @Autowired
    HRService hrService;
    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getAllHrs(@RequestParam String keywords)
    {
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr)
    {
        if (hrService.updateHr(hr) == 1)
        {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("修改失败！");
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles()
    {
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid,Integer[] rids){
        if(hrService.updateHrRole(hrid,rids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    public RespBean deleteHrById(@PathVariable Integer id){
        if(hrService.deleteHrById(id)==1){
            RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
