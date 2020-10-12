package com.guest.assessment.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guest.assessment.emus.EStatus;
import com.guest.assessment.emus.SysConf;
import com.guest.assessment.entity.Profession;
import com.guest.assessment.service.ProfessionService;
import com.guest.assessment.utils.ResultUtil;
import com.guest.assessment.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


//TODO: 添加名称先检查数据库中有没有
@RestController
@Api(value="专业管理", tags={"专业管理"})
@RequestMapping("/profession")
public class ProfessionController {

    @Autowired
    ProfessionService service;

    @ApiOperation(value = "添加专业名称")
    @PostMapping()
    public String add( String name){

        if (StringUtils.isEmpty(name)){
            return ResultUtil.result(SysConf.ERROR,"name不能为空");
        }

        if (service.findByName(name) != null){
            return ResultUtil.result(SysConf.ERROR,"该专业已经添加");
        }

        Profession profession = new Profession();
        profession.setName(name);
        service.add(profession);
        return ResultUtil.result(SysConf.SUCCESS,"添加成功");
    }


    @ApiOperation(value = "删除专业", notes = "根据uid删除专业")
    @DeleteMapping("/delete}")
    public String delete(String uid){
        Profession profession = service.findById(uid);

        if (StringUtils.isEmpty(profession.getName())){
            return ResultUtil.result(SysConf.ERROR,"uid传入错误");
        }
        profession.setStatus(EStatus.DISABLED);
        service.delete(profession);
        return ResultUtil.result(SysConf.SUCCESS,"删除成功");
    }

    @ApiOperation(value="更新专业名称", notes = "根据uid来指定更新对象，name来更新内容")
    @PutMapping()
    public String update(String uid,String name){

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(uid)){
            return ResultUtil.result(SysConf.ERROR,"name或者uid不能为空");
        }

        Profession profession = new Profession();
        profession.setUid(uid);
        profession.setName(name);
        service.update(profession);
        return ResultUtil.result(SysConf.SUCCESS,"更改成功");
    }

    
    @GetMapping("/getList")
    public String getList(@RequestParam(defaultValue = "0", required = false) Integer page,
                          @RequestParam(defaultValue = "10", required = false) Integer size,
                          String keyword){

        Profession bean = new Profession();
        bean.setName(keyword);

        Page<Profession> pageList = service.findAllByNameLikeCriteria(page, size, bean);
        return ResultUtil.result(SysConf.SUCCESS, pageList);
    }

    @PostMapping("/getcount")
    public String getCount(){
        Long count = service.findCount();
        return ResultUtil.result(SysConf.SUCCESS, count);
    }
}
