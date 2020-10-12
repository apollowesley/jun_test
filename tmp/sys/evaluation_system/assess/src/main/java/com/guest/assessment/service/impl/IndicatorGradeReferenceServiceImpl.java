package com.guest.assessment.service.impl;

import com.guest.assessment.emus.EStatus;
import com.guest.assessment.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guest.assessment.dao.IndicatorGradeReferenceRepository;
import com.guest.assessment.entity.IndicatorGradeReference;
import com.guest.assessment.service.IndicatorGradeReferenceService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorGradeReferenceServiceImpl extends BaseServiceImpl<IndicatorGradeReference> implements IndicatorGradeReferenceService {

    @Autowired
    IndicatorGradeReferenceRepository repository;

    @Override
    public IndicatorGradeReference findByName(String name) {
        return repository.findByName(name);
    }



    @Override
    public Page<IndicatorGradeReference> findPageCriteria(Integer page, Integer size, IndicatorGradeReference bean) {
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<IndicatorGradeReference> specification = queryCriteria(bean);
        return repository.findAll(specification, pageRequest);
    }

    @Override
    public List<IndicatorGradeReference> findAllByUids(List<String> list) {
        return repository.findAllByUidInAndStatus(list, EStatus.ENABLE);
    }

    private Specification<IndicatorGradeReference> queryCriteria(IndicatorGradeReference bean){

        return (Root<IndicatorGradeReference> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList();

            if(StringUtils.isNotEmpty(bean.getName())){
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%"));
            }
                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

            if(StringUtils.isNotEmpty(bean.getIndicatorUid())){
                list.add(criteriaBuilder.equal(root.get("indicatorUid").as(String.class), bean.getIndicatorUid()));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

        };
    }
}
