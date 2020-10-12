//package com.guest.assessment;
//
//import com.guest.assessment.emus.SysConf;
//import com.guest.assessment.entity.ProfessionIndicatorRelation;
//import com.guest.assessment.service.ProfessionIndicatorRelationService;
//import com.guest.assessment.utils.ResultUtil;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.*;
//
///**
// * @program: assess
// * @description: 关系测试
// * @author: Xiaodalong
// * @create: 2018-10-11 10:04
// **/
//
//public class relationTest extends AssessApplicationTests {
//
//    @Autowired
//    ProfessionIndicatorRelationService service;
//
//    @Test
//    public void Test(){
//
//        computer("4a41b9ae663d6d3c01663d6d508c0000", "4a41b9ae663d6d3c01663d6d508c0000");
//
//        Iterator<String> iterator = map.keySet().iterator();
//
//        List<String> list = new ArrayList<>();
//
//        while (iterator.hasNext()){
//            list.add(iterator.next());
//        }
//
//        list.forEach( item -> System.out.println(item));
//        Collections.reverse(list);
//        list.forEach( item -> System.out.println(item));
//
//        list.forEach( item -> {
//
//            List<ProfessionIndicatorRelation> relations = map.get(item);
//            double grade = 0.0;
//            for (ProfessionIndicatorRelation relation:
//                 relations) {
//
//                grade +=relation.getGrade() * relation.getIndicatorRatio();
//
//            }
//
//
//        });
//
//
//
//       // System.out.println(ResultUtil.result(SysConf.SUCCESS, map));
//    }
//
//    static Map<String, List<ProfessionIndicatorRelation>> map = new HashMap<>();
//
//    public void computer(String professionUid, String indicatorUid){
//
//        List<ProfessionIndicatorRelation> list = service.findAllSon(professionUid, indicatorUid);
//
//        if (list.size() > 0){
//
//            map.put(indicatorUid, list);
//
//            for (ProfessionIndicatorRelation item:
//                    list) {
//
//                computer(item.getProfessionUid(), item.getIndicatorUid());
//
//            }
//        }
//
//    }
//
//    @Test
//    public void Test1(){
//        String professionUid = "4a41b9ae663d6d3c01663d6d508c0000";
//        String indicatorUid = "4a41b9ae663d6d3c01663d6d508c0000";
//
//
//        c(professionUid, indicatorUid);
//        System.out.println(ResultUtil.result(SysConf.SUCCESS, relations));
//        System.out.println(ResultUtil.result(SysConf.SUCCESS, parents));
//        Double ratio = 1.0;
//        Double grade = 0.0;
//        for (ProfessionIndicatorRelation son:
//             relations) {
//                String parentUid = son.getParentUid();
//            for (ProfessionIndicatorRelation parent:
//                 parents) {
//               if (parentUid == parent.getIndicatorUid()){
//                   parentUid = parent.getIndicatorUid();
//                   ratio *= parent.getIndicatorRatio();
//               }
//            }
//            System.out.println("ratio" + ratio);
//           grade += son.getGrade() * ratio * son.getIndicatorRatio();
//        }
//        System.out.println("grade: " + grade);
//    }
//
//    static ArrayList<ProfessionIndicatorRelation> relations = new ArrayList<>();
//    static ArrayList<ProfessionIndicatorRelation> parents = new ArrayList<>();
//
//    public void c(String professionUid,String indicatorUid) {
//
//        List<ProfessionIndicatorRelation> list = service.findAllSon(professionUid, indicatorUid);
//        if (list.size() > 0) {
//            for (ProfessionIndicatorRelation item :
//                    list) {
//                if (item.getGrade() > 0) {
//                    relations.add(item);
//                } else {
//                    parents.add(item);
//                }
//                c(item.getProfessionUid(), item.getIndicatorUid());
//            }
//        }
//
//    }
//}
