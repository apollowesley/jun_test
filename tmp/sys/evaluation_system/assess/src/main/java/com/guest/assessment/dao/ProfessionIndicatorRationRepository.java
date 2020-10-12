package com.guest.assessment.dao;

import com.guest.assessment.entity.ProfessionIndicatorRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: assess
 * @description: 专业指标关系持久化
 * @author: Xiaodalong
 * @create: 2018-10-06 18:53
 **/

public interface ProfessionIndicatorRationRepository extends JpaRepository<ProfessionIndicatorRelation, String>,
        JpaSpecificationExecutor<ProfessionIndicatorRelation> {

    List<ProfessionIndicatorRelation> findAllByProfessionUidAndParentUidAndStatus(String professionUid, String indicatorUid, int status);

    ProfessionIndicatorRelation findByProfessionUidAndIndicatorUidAndStatus(String professionUid, String indicatorUid, int status);
}

