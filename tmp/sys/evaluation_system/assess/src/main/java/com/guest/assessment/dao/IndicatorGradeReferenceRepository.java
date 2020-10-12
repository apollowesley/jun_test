package com.guest.assessment.dao;

import com.guest.assessment.entity.IndicatorGradeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IndicatorGradeReferenceRepository extends JpaRepository<IndicatorGradeReference,String>,
        JpaSpecificationExecutor<IndicatorGradeReference> {

    IndicatorGradeReference findByName(String name);

    List<IndicatorGradeReference> findAllByUidInAndStatus(List<String> list, Integer status);

}
