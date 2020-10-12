package com.guest.assessment.service;

import com.guest.assessment.entity.Profession;
import com.guest.assessment.entity.ProfessionIndicatorRelation;
import com.guest.assessment.restapi.ProfessionIndicatorRelationController;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @program: assess
 * @description: 专业指标关系服务层
 * @author: Xiaodalong
 * @create: 2018-10-06 18:56
 **/

public interface ProfessionIndicatorRelationService extends BaseService<ProfessionIndicatorRelation> {

    List<ProfessionIndicatorRelation>  addBatch(List<ProfessionIndicatorRelation> list);

    List<ProfessionIndicatorRelation> findAllSon(String prUid, String paUid);

    ProfessionIndicatorRelation findByProfessionUidAndIndicatorUid(String professionUid, String indicatorUid);

    Page<ProfessionIndicatorRelation> findAll(Integer page, Integer size, ProfessionIndicatorRelation bean);
}
