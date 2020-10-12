package com.guest.assessment.dao;

import com.guest.assessment.entity.Indicator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicator, String>, JpaSpecificationExecutor<Indicator> {

    Indicator findByName(String name);

    List<Indicator> findAllByUidInAndStatus(List<String> list, Integer status);
}
