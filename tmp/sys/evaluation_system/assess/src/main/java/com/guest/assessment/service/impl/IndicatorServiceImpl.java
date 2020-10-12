package com.guest.assessment.service.impl;

import com.guest.assessment.emus.EStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.guest.assessment.dao.IndicatorRepository;
import com.guest.assessment.entity.Indicator;
import com.guest.assessment.service.IndicatorService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorServiceImpl extends BaseServiceImpl<Indicator> implements IndicatorService {

    @Autowired
    IndicatorRepository repository;


    @Override
    public Indicator findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Page<Indicator> findAllByNameLikeCriteria(Integer page, Integer size, Indicator bean) {
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<Indicator> specification = queryCriteria(bean);

        return repository.findAll(specification, pageRequest);
    }

    @Override
    public List<Indicator> finaAllByUids(List<String> uidList) {
        return repository.findAllByUidInAndStatus(uidList, EStatus.ENABLE);
    }

    @Override
    public Long findCount(Integer typeStatus) {
        return repository.count((Root<Indicator> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
                -> {
                    List<Predicate> list = new ArrayList();
                    list.add(criteriaBuilder.equal(root.get("typeStatus").as(Integer.class), typeStatus));
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

                }
                );

    }

    /**
     * 条件查询
     */
    private Specification<Indicator> queryCriteria(Indicator bean){

        return (Root<Indicator> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

                List<Predicate> list = new ArrayList();
                if (bean.getTypeStatus() != null){
                    list.add(criteriaBuilder.equal(root.get("typeStatus").as(Integer.class), bean.getTypeStatus()));
                }
                if(bean.getName() != null){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%"));
                }
                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

        };
    }
}
