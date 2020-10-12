//package com.guest.assessment;
//
//import com.guest.assessment.emus.EStatus;
//import com.guest.assessment.entity.Profession;
//import com.guest.assessment.entity.ProfessionGrade;
//import com.guest.assessment.service.ProfessionGradeService;
//import com.guest.assessment.service.ProfessionService;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class ProfessionGradeTests extends AssessApplicationTests {
//    @Autowired
//    ProfessionGradeService professionGradeService;
//
//    @Autowired
//    ProfessionService professionService;
//    @Test
//    public void test(){
//
//        Profession profession = professionService.findByName("数学");
//        ProfessionGrade bean = professionGradeService.findByProfessionUid(profession.getUid());
////        ProfessionGrade grade = new ProfessionGrade();
////        grade.setProfessionUid(profession.getUid());
////        grade.setGrade(100.00);
////        professionGradeService.add(grade);
//
////        bean.setStatus(EStatus.DISABLED);
////        professionGradeService.delete(bean);
//
////        bean.setGrade(32.50);
////        professionGradeService.update(bean);
////
////        List<ProfessionGrade> list = professionGradeService.getList();
////        for (ProfessionGrade grade:
////           list  ) {
////            System.out.println(grade.getGrade());
////        }
//
//    }
//}
