package com.guest.assessment.service.impl;


import com.guest.assessment.emus.EStatus;
import com.guest.assessment.entity.Profession;
import com.guest.assessment.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guest.assessment.dao.IndicatorGradeRepository;
import com.guest.assessment.entity.IndicatorGrade;
import com.guest.assessment.service.IndicatorGradeService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorGradeServiceImpl extends BaseServiceImpl<IndicatorGrade> implements IndicatorGradeService {

    @Autowired
    IndicatorGradeRepository repository;


    @Override
    public IndicatorGrade findByIndicatorUid(String uid) {
        return repository.findByIndicatorUid(uid);
    }

    @Override
    public Page<IndicatorGrade> findPage(Integer page, Integer size, IndicatorGrade bean) {
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<IndicatorGrade> specification = queryCriteria(bean);

        return repository.findAll(specification, pageRequest);
    }

    @Override
    public IndicatorGrade find(String professionUid, String indicatorUid) {
        return repository.findByProfessionUidAndIndicatorUidAndStatus(professionUid,indicatorUid,EStatus.ENABLE);
    }

    /**
     * 条件查询
     */
    private Specification<IndicatorGrade> queryCriteria(IndicatorGrade bean){

        return (Root<IndicatorGrade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList();

            if(StringUtils.isNotEmpty(bean.getProfessionUid())){
                list.add(criteriaBuilder.equal(root.get("professionUid").as(String.class), bean.getProfessionUid()));
            }

            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

        };
    }
}
