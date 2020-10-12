package com.guest.assessment.dao;

import com.guest.assessment.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ProfessionRepository extends JpaRepository<Profession, String>, JpaSpecificationExecutor<Profession> {

    Profession findByNameAndStatus(String name, Integer status);


    List<Profession> findAllByUidInAndStatus(List<String> uids, Integer status);
}
