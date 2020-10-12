package com.guest.assessment.service.impl;

import com.guest.assessment.dao.ProfessionRepository;
import com.guest.assessment.emus.EStatus;
import com.guest.assessment.entity.Indicator;
import com.guest.assessment.entity.Profession;
import com.guest.assessment.service.ProfessionService;
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

@Service
public class ProfessionServiceImpl extends BaseServiceImpl<Profession> implements ProfessionService {

    @Autowired
    ProfessionRepository repository;

    @Override
    public Profession findByName(String name) {
        return  repository.findByNameAndStatus(name,EStatus.ENABLE);
    }



    @Override
    public Page<Profession> findAllByNameLikeCriteria(Integer page, Integer size, Profession bean) {
        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<Profession> specification = queryCriteria(bean);

        return repository.findAll(specification, pageRequest);
    }

    @Override
    public List<Profession> findAllByUids(List<String> uids) {
        return repository.findAllByUidInAndStatus(uids, EStatus.ENABLE);
    }

    @Override
    public Long findCount() {
        return repository.count((Root<Profession> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));
    }


    /**
     * 条件查询
     */
    private Specification<Profession> queryCriteria(Profession bean){

        return (Root<Profession> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList();

            if(bean.getName() != null){
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + bean.getName() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), EStatus.ENABLE));

            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

        };
    }
}
