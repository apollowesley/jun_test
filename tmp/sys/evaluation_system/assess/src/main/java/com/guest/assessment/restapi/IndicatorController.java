package com.guest.assessment.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guest.assessment.emus.EStatus;
import com.guest.assessment.emus.SysConf;
import com.guest.assessment.entity.Indicator;
import com.guest.assessment.service.IndicatorService;
import com.guest.assessment.utils.ResultUtil;
import com.guest.assessment.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "IndicatorController", tags = {"指标和观测点管理"})
@RequestMapping("/indicator")
public class IndicatorController {

    @Autowired
    IndicatorService service;

    @ApiOperation(value = "添加指标", notes = "根据名称和属性添加指标，默认是指标")
    @PostMapping()
    public String insert(String name, Integer typeStatus){

        if (StringUtils.isEmpty(name)){
            return ResultUtil.result(SysConf.ERROR,"name不能为空");
        }

        Indicator bean = new Indicator();
        bean.setName(name);
        if (typeStatus != null){
            bean.setTypeStatus(typeStatus);
        }

        service.add(bean);

        return ResultUtil.result(SysConf.SUCCESS,"添加成功");
    }

    @ApiOperation(value = "删除指标")
    @PostMapping("/delete")
    public String delete(String uid){

        Indicator bean = service.findById(uid);

        if (StringUtils.isEmpty(bean.getName())){
            return ResultUtil.result(SysConf.ERROR, "传入的uid有误");
        }

        bean.setStatus(EStatus.DISABLED);
        service.delete(bean);

        return ResultUtil.result(SysConf.SUCCESS,"删除成功");
    }

    @ApiOperation(value = "更新指标")
    @PutMapping()
    public String update(String uid, String name, Integer typeStatus){

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(uid) || typeStatus == null){
            return ResultUtil.result(SysConf.ERROR,"name，uid，typeStatus不能为空");
        }

        Indicator indicator = new Indicator();
        indicator.setUid(uid);
        indicator.setName(name);
        indicator.setTypeStatus(typeStatus);
        service.update(indicator);

        return ResultUtil.result(SysConf.SUCCESS, "更改成功");
    }

    @GetMapping("/getlist")
    public String getPage(@RequestParam(defaultValue = "0", required = false) Integer page,
                          @RequestParam(defaultValue = "10", required = false) Integer size,
                          Integer typeStatus,
                          String keyword){

        Indicator bean = new Indicator();
        bean.setName(keyword);
        bean.setTypeStatus(typeStatus);

        Page<Indicator> pageList = service.findAllByNameLikeCriteria(page,size,bean);

        return  ResultUtil.result(SysConf.SUCCESS,pageList);
    }

    @ApiOperation(value = "根据类型查找指标或者观测点,")
    @PostMapping("/getcount")
    public String getCount(@RequestParam(defaultValue = "0", required = false) Integer typeStatus){

        Long count = service.findCount(typeStatus);
        return ResultUtil.result(SysConf.SUCCESS, count);
    }

}
