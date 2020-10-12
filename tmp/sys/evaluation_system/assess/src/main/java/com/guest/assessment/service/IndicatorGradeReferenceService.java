package com.guest.assessment.service;

import com.guest.assessment.entity.IndicatorGradeReference;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndicatorGradeReferenceService extends BaseService<IndicatorGradeReference> {

    IndicatorGradeReference findByName(String name);

    Page<IndicatorGradeReference> findPageCriteria(Integer page, Integer size, IndicatorGradeReference bean);

    List<IndicatorGradeReference>  findAllByUids(List<String> list);
}
