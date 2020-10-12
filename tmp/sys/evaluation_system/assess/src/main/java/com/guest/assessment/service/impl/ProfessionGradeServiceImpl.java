package com.guest.assessment.service.impl;

import com.guest.assessment.emus.EStatus;
import com.guest.assessment.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guest.assessment.dao.ProfessionGradeRepository;
import com.guest.assessment.entity.ProfessionGrade;
import com.guest.assessment.service.ProfessionGradeService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionGradeServiceImpl extends BaseServiceImpl<ProfessionGrade> implements ProfessionGradeService {

    @Autowired
    ProfessionGradeRepository repository;


    @Override
    public ProfessionGrade findByProfessionUid(String uid) {
        return repository.findByProfessionUidAndStatus(uid,EStatus.ENABLE);
    }

    @Override
    public Page<ProfessionGrade> findAllCriteria(Integer page, Integer size, ProfessionGrade bean) {
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<ProfessionGrade> specification = queryCriteria(bean);

        return repository.findAll(specification, pageRequest);
    }

    @Override
    public Long getCount() {
        return repository.count();
    }

    private Specification<ProfessionGrade> queryCriteria(ProfessionGrade bean) {

        return (Root<ProfessionGrade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList();

            if (StringUtils.isNotEmpty(bean.getProfessionUid())) {
                list.add(criteriaBuilder.like(root.get("professionUid").as(String.class), bean.getProfessionUid()));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

        };

    }
}
