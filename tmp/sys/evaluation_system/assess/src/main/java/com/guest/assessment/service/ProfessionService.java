package com.guest.assessment.service;

import com.guest.assessment.entity.Profession;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfessionService  extends BaseService<Profession> {

    Profession findByName(String name);

    Page<Profession> findAllByNameLikeCriteria(Integer page, Integer size, Profession bean);

    List<Profession> findAllByUids(List<String> uids);


    Long findCount();
}
