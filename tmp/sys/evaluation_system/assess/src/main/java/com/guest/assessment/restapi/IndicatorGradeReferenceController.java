package com.guest.assessment.restapi;

import com.guest.assessment.emus.EStatus;
import com.guest.assessment.emus.SysConf;
import com.guest.assessment.entity.Indicator;
import com.guest.assessment.entity.IndicatorGradeReference;
import com.guest.assessment.service.IndicatorGradeReferenceService;
import com.guest.assessment.service.IndicatorService;
import com.guest.assessment.utils.ResultUtil;
import com.guest.assessment.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: assess
 * @description: 指标得分点参考
 * @author: Xiaodalong
 * @create: 2018-10-06 16:48
 **/
@RestController
@Api(value = "指标得分参考", tags = {"指标得分参考点的管理"})
@RequestMapping("/indicatorGradeReference")
public class IndicatorGradeReferenceController {

    @Autowired
    IndicatorGradeReferenceService service;

    @Autowired
    IndicatorService indicatorService;

    @PostMapping()
    @ApiOperation(value = "添加指标得分点参考")
    public String add(String name, String description, String indicatorUid, Double grade){

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(description) || grade < 0){
            return ResultUtil.result(SysConf.ERROR,"name,indicatorUid,grade不能为空");
        }
        IndicatorGradeReference bean = new IndicatorGradeReference();
        bean.setName(name);
        bean.setDescription(description);
        bean.setIndicatorUid(indicatorUid);
        bean.setGrade(grade);
        service.add(bean);

        return ResultUtil.result(SysConf.SUCCESS,"添加成功");
    }

    @PutMapping()
    @ApiOperation(value = "修改指标得分点参考")
    public String update(String uid, String name, String description, String indicatorUid, Double grade){
        if (StringUtils.isEmpty(uid) ||StringUtils.isEmpty(name) || StringUtils.isEmpty(description) ||
                grade < 0){
            return ResultUtil.result(SysConf.ERROR,"name,indicatorUid,grade不能为空");
        }

        IndicatorGradeReference bean = new IndicatorGradeReference();
        bean.setUid(uid);
        bean.setName(name);
        bean.setDescription(description);
        bean.setIndicatorUid(indicatorUid);
        bean.setGrade(grade);
        service.update(bean);
        return ResultUtil.result(SysConf.SUCCESS,"修改成功");
    }

    @ApiOperation(value = "删除指标得分点参考")
    @PostMapping("/delete")
    public String delete(String uid){

        IndicatorGradeReference bean = service.findById(uid);

        if (StringUtils.isEmpty(bean.getName())){
            ResultUtil.result(SysConf.ERROR,"uid错误");
        }
        bean.setStatus(EStatus.DISABLED);
        service.delete(bean);
        return ResultUtil.result(SysConf.SUCCESS,"删除成功");
    }

    /**
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/getList")
    public String getPageByKeyword(@RequestParam(defaultValue = "0", required = false) Integer page,
                                   @RequestParam(defaultValue = "10", required = false) Integer size,
                                   String keyword, String indicatorUid){
        IndicatorGradeReference bean = new IndicatorGradeReference();
        if (StringUtils.isNotEmpty(keyword)){
           bean.setName(keyword);
        }

        if (StringUtils.isNotEmpty(indicatorUid)){
            bean.setIndicatorUid(indicatorUid);
        }

        Page<IndicatorGradeReference> pageList = service.findPageCriteria(page, size, bean);
        HashMap<String, Object> map = new HashMap<>();

        if(StringUtils.isEmpty(indicatorUid)){
            ArrayList<String> indicatorUids = new ArrayList<>();
            pageList.forEach( item -> indicatorUids.add(item.getIndicatorUid()));

            List<Indicator> list = indicatorService.finaAllByUids(indicatorUids);
            map.put("inidcator", list);
        }

        map.put("indicatorGradeReference", pageList);


        return ResultUtil.result(SysConf.SUCCESS,map);
    }

    @ApiOperation(value = "通过indicatorUid查找所有的参考")
    public String getIndicatorReference(String uid){

        return ResultUtil.result(SysConf.SUCCESS,uid);
    }
}
