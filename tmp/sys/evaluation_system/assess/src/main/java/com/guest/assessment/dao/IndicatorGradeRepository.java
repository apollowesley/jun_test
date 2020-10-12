package com.guest.assessment.dao;

import com.guest.assessment.entity.IndicatorGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IndicatorGradeRepository extends JpaRepository<IndicatorGrade, String>,
        JpaSpecificationExecutor<IndicatorGrade> {

    IndicatorGrade findByIndicatorUid(String uid);


    IndicatorGrade findByProfessionUidAndIndicatorUidAndStatus(String professionUid, String indicatorUid, int enable);

}
