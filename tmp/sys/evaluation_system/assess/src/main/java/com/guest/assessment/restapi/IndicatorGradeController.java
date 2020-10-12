package com.guest.assessment.restapi;


import com.guest.assessment.emus.EStatus;
import com.guest.assessment.emus.SysConf;
import com.guest.assessment.entity.*;
import com.guest.assessment.service.*;
import com.guest.assessment.utils.JsonUtils;
import com.guest.assessment.utils.ResultUtil;
import com.guest.assessment.utils.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/indicatorGrade")
@Api(value = "IndicatorGradeController", tags ={"指标成绩的管理"})
public class IndicatorGradeController {

    @Autowired
    IndicatorGradeService service;

    @Autowired
    IndicatorService indicatorService;

    @Autowired
    IndicatorGradeReferenceService referenceService;

    @Autowired
    ProfessionService professionService;

    @Autowired
    ProfessionIndicatorRelationService relationService;


    @PostMapping()
    public String add(String referenceUid, String indicatorUid, String professionUid, Double grade){

        if (StringUtils.isEmpty(referenceUid) || StringUtils.isEmpty(indicatorUid) ||
                StringUtils.isEmpty(Double.toString(grade) ) || StringUtils.isEmpty(professionUid)){
            return ResultUtil.result(SysConf.ERROR,"referenceUid、indicatorUid、grade不能为空");
        }

        IndicatorGrade bean = new IndicatorGrade();
        bean.setIndicatorUid(indicatorUid);
        bean.setReferenceUid(referenceUid);
        bean.setProfessionUid(professionUid);
        bean.setGrade(grade);
        service.add(bean);

        //如果设定专业和指标关系后后才添加参考
        ProfessionIndicatorRelation relation = relationService.findByProfessionUidAndIndicatorUid(professionUid, indicatorUid);
        if (relation != null){
            relation.setGrade(grade);
            relationService.update(relation);
        }
        return ResultUtil.result(SysConf.SUCCESS, "添加成功");
    }

    @PostMapping("/delete")
    public String delete(String uid){

        IndicatorGrade bean = service.findById(uid);

        if (StringUtils.isEmpty(bean.getIndicatorUid())){

            return ResultUtil.result(SysConf.ERROR, "uid错误");
        }

        bean.setStatus(EStatus.DISABLED);
        service.delete(bean);

        return ResultUtil.result(SysConf.SUCCESS, "删除成功");
    }

    @PutMapping()
    public String update(String uid, String referenceUid, String indicatorUid, String professionUid, Double grade){

        if (StringUtils.isEmpty(uid) ||StringUtils.isEmpty(referenceUid) || StringUtils.isEmpty(indicatorUid) ||
                StringUtils.isEmpty(Double.toString(grade)) || StringUtils.isEmpty(professionUid)){
            return ResultUtil.result(SysConf.ERROR,"uid、referenceUid、indicatorUid、grade不能为空");
        }

        IndicatorGrade bean = new IndicatorGrade();
        bean.setUid(uid);
        bean.setIndicatorUid(indicatorUid);
        bean.setReferenceUid(referenceUid);
        bean.setProfessionUid(professionUid);
        bean.setGrade(grade);
        service.update(bean);

        ProfessionIndicatorRelation relation = relationService.findByProfessionUidAndIndicatorUid(professionUid, indicatorUid);
        if (relation != null){
            relation.setGrade(grade);
            relationService.update(relation);
        }

        return ResultUtil.result(SysConf.SUCCESS, "更改成功");
    }


    /**
     * 指标名称
     * 专业名称
     * 参考名称
     * 得分
     */
    @GetMapping("/getList")
    public String getPage(@RequestParam(defaultValue = "0", required = false) Integer page,
                          @RequestParam(defaultValue = "10", required = false) Integer size,
                          String professionUid){

        IndicatorGrade bean = new IndicatorGrade();

        bean.setProfessionUid(professionUid);


        Page<IndicatorGrade> list = service.findPage(page, size, bean);

        List<IndicatorGrade> content = list.getContent();
        for (IndicatorGrade item : content) {
            String indicatorUid = item.getIndicatorUid();
            String referenceUid = item.getReferenceUid();
            professionUid = item.getProfessionUid();

            item.setIndicator(indicatorService.findById(indicatorUid));
            item.setProfession(professionService.findById(professionUid));
            item.setReference(referenceService.findById(referenceUid));
        }

        return ResultUtil.result(SysConf.SUCCESS, list);

    }


}
