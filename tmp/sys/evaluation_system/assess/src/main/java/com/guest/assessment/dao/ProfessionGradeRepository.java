package com.guest.assessment.dao;

import com.guest.assessment.entity.Profession;
import com.guest.assessment.entity.ProfessionGrade;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProfessionGradeRepository extends JpaRepository<ProfessionGrade, String>, JpaSpecificationExecutor<ProfessionGrade> {

    ProfessionGrade findByProfessionUidAndStatus(String uid, Integer status);

}
