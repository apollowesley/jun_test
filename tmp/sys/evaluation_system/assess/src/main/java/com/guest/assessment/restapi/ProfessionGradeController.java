package com.guest.assessment.restapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guest.assessment.emus.SysConf;
import com.guest.assessment.entity.ProfessionGrade;
import com.guest.assessment.service.ProfessionGradeService;
import com.guest.assessment.service.ProfessionService;
import com.guest.assessment.utils.ResultUtil;

import io.swagger.annotations.Api;


@RestController
@Api(value = "ProfessionGradeController", tags = "专业成绩的接口")
@RequestMapping("/professionGrade")
public class ProfessionGradeController {

    @Autowired
    ProfessionGradeService service;

    @Autowired
    ProfessionService professionService;

    @GetMapping("/getList")
    public String getPage(@RequestParam(defaultValue = "0", required = false) Integer page,
                          @RequestParam(defaultValue = "10", required = false) Integer size,
                          String professionUid){

        ProfessionGrade bean = new ProfessionGrade();
        bean.setProfessionUid(professionUid);
        Page<ProfessionGrade> pageList = service.findAllCriteria(page,size,bean);

        pageList.forEach( item -> item.setProfession(professionService.findById(item.getProfessionUid())));

        return ResultUtil.result(SysConf.SUCCESS, pageList);
    }

    @PostMapping("/getcount")
    public String getCount(){
        Long count  = service.getCount();
        return ResultUtil.result(SysConf.SUCCESS, count);
    }


}
