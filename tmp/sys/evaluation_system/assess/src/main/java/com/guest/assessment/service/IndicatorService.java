package com.guest.assessment.service;

import com.guest.assessment.entity.Indicator;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndicatorService extends BaseService<Indicator> {

    Indicator findByName(String name);

    Page<Indicator> findAllByNameLikeCriteria(Integer page, Integer size, Indicator bean);

    List<Indicator> finaAllByUids(List<String> uidList);

    Long findCount(Integer typeStatus);



}
