package com.zhengjieblog.pocket.repo.spec;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PocketSpec implements Specification<Pocket> {

    private Long userID;

    private Integer year;

    private Integer month;

    private boolean isnull = false;

    public PocketSpec(Long userID, Integer year, Integer month){
        this.month = month;
        this.year = year;
        this.userID = userID;
    }

    public PocketSpec(Long userID, boolean isnull) {
        this.userID = userID;
        this.isnull = isnull;
    }

    @Override
    public Predicate toPredicate(Root<Pocket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        Join<User,Pocket> join = root.join("user", JoinType.LEFT);
        if(null != userID){
            list.add(cb.equal(join.get("id").as(Long.class),userID));
        }

        if(null != year){
            list.add(cb.equal(root.get("year").as(Integer.class),year));
        }

        if(null != month){
            list.add(cb.equal(root.get("month").as(Integer.class),month));
        }

        if(isnull){
            list.add(cb.isEmpty(root.get("pocketDetails")));
        } else {
            list.add(cb.isNotEmpty(root.get("pocketDetails")));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }
}
