package com.guest.assessment.service;

import com.guest.assessment.entity.IndicatorGrade;
import org.springframework.data.domain.Page;

public interface IndicatorGradeService extends BaseService<IndicatorGrade> {

    IndicatorGrade findByIndicatorUid(String uid);

    Page<IndicatorGrade> findPage(Integer page, Integer size, IndicatorGrade bean);

    IndicatorGrade find(String professionUid, String indicatorUid);
}
