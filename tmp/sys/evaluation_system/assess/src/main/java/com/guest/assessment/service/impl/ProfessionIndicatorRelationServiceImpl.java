package com.guest.assessment.service.impl;

import com.guest.assessment.dao.ProfessionIndicatorRationRepository;
import com.guest.assessment.emus.EStatus;
import com.guest.assessment.entity.Profession;
import com.guest.assessment.entity.ProfessionGrade;
import com.guest.assessment.entity.ProfessionIndicatorRelation;
import com.guest.assessment.service.ProfessionIndicatorRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: assess
 * @description: 专业指标关系服务层实现
 * @author: Xiaodalong
 * @create: 2018-10-06 18:58
 **/
@Service
public class ProfessionIndicatorRelationServiceImpl extends BaseServiceImpl<ProfessionIndicatorRelation>
        implements ProfessionIndicatorRelationService {

    @Autowired
    ProfessionIndicatorRationRepository repository;
    @Override
    public List<ProfessionIndicatorRelation> addBatch(List<ProfessionIndicatorRelation> list) {
        return repository.saveAll(list);
    }

    @Override
    public List<ProfessionIndicatorRelation> findAllSon(String prUid, String paUid) {
        return  repository.findAllByProfessionUidAndParentUidAndStatus(prUid, paUid, EStatus.ENABLE);

    }

    @Override
    public ProfessionIndicatorRelation findByProfessionUidAndIndicatorUid(String professionUid, String indicatorUid) {
        return repository.findByProfessionUidAndIndicatorUidAndStatus(professionUid,indicatorUid, EStatus.ENABLE);
    }

    @Override
    public Page<ProfessionIndicatorRelation> findAll(Integer page, Integer size, ProfessionIndicatorRelation bean) {
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<ProfessionIndicatorRelation> specification = queryCriteria(bean);
        return repository.findAll(specification, pageRequest);
    }

    /**
     * 条件查询
     */
    private Specification<ProfessionIndicatorRelation> queryCriteria(ProfessionIndicatorRelation bean) {

        return (Root<ProfessionIndicatorRelation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList();

            if (bean.getProfessionUid() != null) {
                list.add(criteriaBuilder.equal(root.get("professionUid").as(String.class), bean.getProfessionUid()));
            }

            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
    }

}

