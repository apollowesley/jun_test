package com.guest.assessment.restapi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guest.assessment.emus.EStatus;
import com.guest.assessment.emus.SysConf;
import com.guest.assessment.entity.*;
import com.guest.assessment.service.*;
import com.guest.assessment.utils.ResultUtil;
import com.guest.assessment.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: assess
 * @description: 专业和指标的关系
 * @author: Xiaodalong
 * @create: 2018-10-06 18:41
 **/
@RestController
@RequestMapping("/professionIndicatorRelation")
@Api(value = "专业和指标的关系管理", tags = "专业和指标的关系管理")
public class ProfessionIndicatorRelationController {

    @Autowired
    ProfessionIndicatorRelationService service;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private IndicatorGradeService indicatorGradeService;

    @Autowired
    private ProfessionGradeService professionGradeService;

    private ArrayList<ProfessionIndicatorRelation> sonList = new ArrayList<>();

    private ArrayList<ProfessionIndicatorRelation> parentList = new ArrayList<>();

    /**
     * [{indicatorUid:"", professionUid: "", parentUid: "", indicatorRatio: ""}]
     * 
     * @author xuzhixiang
     * @date 2018年10月16日上午10:34:41
     */
    @PostMapping()
    @ApiOperation(value="添加指标和专业关系,指标和指标关系", notes="传入jsonArr,每个josn对象有indicatorUid、professionUid、" +
            "parentUid" + "、indicatorRatio")
    public String add(String jsonArr){

        double ratio = 0.0;
        ArrayList<ProfessionAddJsonBean> list = Json2ArrayList(jsonArr);
        for (ProfessionAddJsonBean bean : list) {
            ratio += bean.getIndicatorRatio();
        }


        if (ratio != 1){
            return ResultUtil.result(SysConf.ERROR, "比例相加等于1" );
        }

        ArrayList<ProfessionIndicatorRelation> beanList = new ArrayList<>();

        list.forEach(bean -> {
            ProfessionIndicatorRelation relation = new ProfessionIndicatorRelation();
            relation.setProfessionUid(bean.getProfessionUid());
            relation.setIndicatorUid(bean.getIndicatorUid());
            relation.setIndicatorRatio(bean.getIndicatorRatio());
            relation.setParentUid(bean.getParentUid());
            beanList.add(relation);

            IndicatorGrade indicatorGrade = indicatorGradeService.find(relation.getProfessionUid(),relation.getIndicatorUid());
            if (indicatorGrade != null){
                relation.setGrade(indicatorGrade.getGrade());
            }


        });

        service.addBatch(beanList);

        return ResultUtil.result(SysConf.SUCCESS, "添加成功" );

    }

    @PutMapping
    @ApiOperation(value = "指标专业关系的修改", notes = "传入jsonArr,每个josn对象有uid、indicatorUid、professionUid、" +
            "parentUid" + "、indicatorRatio")
    public String update(String jsonArr){
    	
    	if(StringUtils.isEmpty(jsonArr)) {
    		return ResultUtil.result(SysConf.ERROR, "必填项不能为空");
    	}
        double ratio = 0.0;

        ArrayList<ProfessionUpdateJsonBean> list = new Gson().fromJson(jsonArr, new TypeToken<List<ProfessionUpdateJsonBean>>() {
        }.getType() );

        for (ProfessionUpdateJsonBean bean : list) {
            ratio += bean.getIndicatorRatio();
        }

        if (ratio != 1){
            return ResultUtil.result(SysConf.ERROR, "同级别指标比例相加等于1" );
        }

        ArrayList<ProfessionIndicatorRelation> beanList = new ArrayList<>();

        list.forEach(bean -> {

            ProfessionIndicatorRelation relation = new ProfessionIndicatorRelation();
            relation.setUid(bean.getUid());
            relation.setIndicatorUid(bean.getIndicatorUid());
            relation.setProfessionUid(bean.getProfessionUid());
            relation.setIndicatorRatio(bean.getIndicatorRatio());
            relation.setParentUid(bean.getParentUid());

            IndicatorGrade indicatorGrade = indicatorGradeService.find(relation.getProfessionUid(),relation.getIndicatorUid());
            if (indicatorGrade != null){
                relation.setGrade(indicatorGrade.getGrade());
            }


            beanList.add(relation);

        });

        service.addBatch(beanList);
        return ResultUtil.result(SysConf.SUCCESS, "修改成功" );
    }

    @ApiOperation(value = "根据uid删除指标，但是同一级别的指标比例需要改变修改参数参考更新指标")
    @PostMapping("/delete")
    public String delete(String uid, String jsonArr){

        ProfessionIndicatorRelation relation = service.findById(uid);

        if (StringUtils.isEmpty(relation.getUid())){
            return ResultUtil.result(SysConf.ERROR, "uid错误");
        }

        relation.setStatus(EStatus.DISABLED);
        service.delete(relation);
        update(jsonArr);
        return ResultUtil.result(SysConf.SUCCESS, "删除成功");
    }



    public String getSon(String professionUid, String indicatorUid){

        if(StringUtils.isEmpty(professionUid) || StringUtils.isEmpty(indicatorUid)){
            return ResultUtil.result(SysConf.ERROR, "professionUid,或者parentUid不能为空");
        }

        List<ProfessionIndicatorRelation> list = service.findAllSon(professionUid, indicatorUid);

        for (ProfessionIndicatorRelation item:
             list) {
            item.setIndicator(indicatorService.findById(item.getIndicatorUid()));
            item.setProfession(professionService.findById(item.getProfessionUid()));
            item.setGrade(getGrade(item.getProfessionUid(), item.getIndicatorUid()));
        }

        return ResultUtil.result(SysConf.SUCCESS,list);
    }



    @GetMapping("/getallson")
    public String getAllSon(String professionUid){
        //TODO: 招生录取情况数据不能多次添加
        //TODO: 有一个指标不能用教师教研
        RelationJson relationJson = new RelationJson();
        relationJson.name =  professionService.findById(professionUid).getName() +getGrade(professionUid, professionUid) ;
        relationJson.value = new Double[]{getGrade(professionUid, professionUid),1.0};

        List<ProfessionIndicatorRelation> list = service.findAllSon(professionUid, professionUid);
        List<RelationJson> relationJsonList = new ArrayList<>();
        relationJson.children =relationJsonList;
        queryAllSon(list, relationJsonList);

        //添加成专业成绩到数据库
        Double grade = 0.0;
        for (RelationJson item:
                relationJsonList) {
            grade += item.value[0] * item.value[1];

        }
        DecimalFormat df = new DecimalFormat("0.00");
        grade = Double.valueOf(df.format(grade));

        ProfessionGrade bean = professionGradeService.findByProfessionUid(professionUid);
        if (bean == null){
            bean = new ProfessionGrade();
            bean.setProfessionUid(professionUid);
            bean.setGrade(grade);

        }else{

            bean.setGrade(grade);
        }
        professionGradeService.add(bean);
        return ResultUtil.result(SysConf.SUCCESS, relationJson);
    }

    class RelationJson{
         String name;
         Double[] value;
         List<RelationJson> children;
    }

    /**
     *
     * @param list
     * @param relationJsonList
     */
    private void queryAllSon(List<ProfessionIndicatorRelation> list, List<RelationJson> relationJsonList){
        if (list.size() > 0) {
            for (ProfessionIndicatorRelation item :
                    list) {
//
                RelationJson bean = new RelationJson();
                bean.name = indicatorService.findById(item.getIndicatorUid()).getName() + getGrade(item.getProfessionUid(), item.getIndicatorUid());
                bean.value =new Double[]{getGrade(item.getProfessionUid(), item.getIndicatorUid()), item.getIndicatorRatio()};
                relationJsonList.add(bean);

                ArrayList<RelationJson> relationJsons = new ArrayList<>();
                bean.children = relationJsons;
                queryAllSon(service.findAllSon(item.getProfessionUid(), item.getIndicatorUid()), relationJsons);

            }
        }
    }
    
    @GetMapping("/findBrother")
    public String findBrother(String professionUid, String parentUid){
        if(StringUtils.isEmpty(professionUid) || StringUtils.isEmpty(parentUid)){
            return ResultUtil.result(SysConf.ERROR, "professionUid,或者parentUid不能为空");
        }

        List<ProfessionIndicatorRelation> list = service.findAllSon(professionUid, parentUid);

        for (ProfessionIndicatorRelation item:
                list) {
            item.setIndicator(indicatorService.findById(item.getIndicatorUid()));
            item.setProfession(professionService.findById(item.getProfessionUid()));
            item.setGrade(getGrade(item.getProfessionUid(), item.getIndicatorUid()));
        }

        return ResultUtil.result(SysConf.SUCCESS,list);

    }

    @GetMapping("/getList")
    public String getList(@RequestParam(defaultValue = "0", required = false) Integer page,
                          @RequestParam(defaultValue = "10", required = false) Integer size,
                          String keyword){

        ProfessionIndicatorRelation bean = new ProfessionIndicatorRelation();
        if(StringUtils.isNotEmpty(keyword)) {
        	bean.setProfessionUid(keyword);	
        }
        
        Page<ProfessionIndicatorRelation> list = service.findAll(page, size, bean);

        list.forEach( item -> {
            item.setIndicator(indicatorService.findById(item.getIndicatorUid()));
            if(item.getParentUid().equals(item.getProfessionUid())) {
            		
            } else {
            	item.setParentIndicator(indicatorService.findById(item.getParentUid()));
            }
            
            item.setProfession(professionService.findById(item.getProfessionUid()));
        });
        

        return ResultUtil.result(SysConf.SUCCESS, list);
    }

    /**
     * 1.在数据库中找出成绩，如果不为空，显示
     * 如果为空,则寻找所有的子节点，如果是叶子节点放到sonList，其他节点放到parentList
     * 遍历每一个叶子节点的，找到所有父节点。计算比例。根据比例计算成绩。
     *
     * 指标的成绩不能存到数据库中，
     * @param professionUid
     * @param indicatorUid
     * @return
     */
    public Double getGrade(String professionUid, String indicatorUid){
        //1.这个节点如果有成绩直接返回这个成绩
        ProfessionIndicatorRelation bean = service.findByProfessionUidAndIndicatorUid(professionUid, indicatorUid);

        if (bean != null && bean.getGrade() > 0){
            return bean.getGrade();
        }

        sonList.clear();
        parentList.clear();
        // 把所有叶子节点放在sonList,其他节点放在parentList
        computer(professionUid, indicatorUid);

        Double grade = 0.0;
        for (ProfessionIndicatorRelation son:
                sonList) {
            Double ratio = son.getIndicatorRatio();
            String parentUid = son.getParentUid();
            for (ProfessionIndicatorRelation parent:
                    parentList) {
                if (parentUid.equals(parent.getIndicatorUid())){
                    parentUid = parent.getIndicatorUid();
                    ratio *= parent.getIndicatorRatio();
                }
            }
            System.out.println("ratio" + ratio);
            grade += son.getGrade() * ratio;
        }
        ProfessionGrade professionGrade = professionGradeService.findByProfessionUid(professionUid);
        if (professionGrade != null && professionUid.equals(indicatorUid)){
           return professionGrade.getGrade();
       }


        return grade;
    }

    /**
     * 是根据有无成绩来分别是否为叶子节点
     * @param professionUid
     * @param indicatorUid
     */
    private void computer(String professionUid,String indicatorUid) {

        List<ProfessionIndicatorRelation> list = service.findAllSon(professionUid, indicatorUid);
        if (list.size() > 0) {
            for (ProfessionIndicatorRelation item :
                    list) {
                if (item.getGrade() > 0) {
                    sonList.add(item);
                } else {
                    parentList.add(item);
                }
                computer(item.getProfessionUid(), item.getIndicatorUid());
            }
        }

    }



    /**
     *
     * @param json
     * @return ArrayList<ProfessionAddJsonBean>
     */
    private ArrayList<ProfessionAddJsonBean> Json2ArrayList(String json){

        Gson gson = new Gson();
        ArrayList<ProfessionAddJsonBean> list = gson.fromJson(json, new TypeToken<List<ProfessionAddJsonBean>>() {
        }.getType() );

        return list;
    }




}
