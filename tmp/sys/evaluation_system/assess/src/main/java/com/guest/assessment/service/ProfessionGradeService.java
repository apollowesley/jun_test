package com.guest.assessment.service;

import com.guest.assessment.entity.ProfessionGrade;
import org.springframework.data.domain.Page;

public interface ProfessionGradeService extends BaseService<ProfessionGrade> {

    ProfessionGrade findByProfessionUid(String uid);

    Page<ProfessionGrade> findAllCriteria(Integer page, Integer size, ProfessionGrade bean);

    Long getCount();
}
