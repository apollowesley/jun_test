package com.leo.vhr.controller.system.basic;

import com.leo.vhr.model.JobLevel;
import com.leo.vhr.model.RespBean;
import com.leo.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/17
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController
{
    @Autowired
    JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevels()
    {
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevels(@RequestBody JobLevel jobLevel)
    {
        if (jobLevelService.addJobLevels(jobLevel) == 1)
        {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updateJobLevels(@RequestBody JobLevel jobLevel)
    {
        if (jobLevelService.updateJobLevels(jobLevel) == 1)
        {
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean delJobLevelById(@PathVariable Integer id)
    {
        if (jobLevelService.delJobLevelById(id) == 1)
        {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @DeleteMapping("/")
    public RespBean delJobLevelByIds(Integer[] ids)
    {
        if (jobLevelService.delJobLevelByIds(ids) == ids.length)
        {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
