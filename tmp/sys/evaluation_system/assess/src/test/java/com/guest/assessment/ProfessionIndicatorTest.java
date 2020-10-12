//package com.guest.assessment;
//
//import com.guest.assessment.service.ProfessionIndicatorRelationService;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @program: assess
// * @description: test
// * @author: Xiaodalong
// * @create: 2018-10-06 22:07
// **/
//public  class ProfessionIndicatorTest extends AssessApplicationTests {
//
//    @Autowired
//    ProfessionIndicatorRelationService service;
//    @Test
//    public void test(){
////        String json ="[{'uid':'4a41b9ae6649bf92016649bfbf040000',\"indicatorUid\":\"4a41b9ae663e60a101663e60b4f20000\",\"professionUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"parentUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"indicatorRatio\":0.7},{'uid':'4a41b9ae6649bf92016649bfbf180001',\"indicatorUid\":\"4a41b9ae66484a4f0166484c8bbc0000\",\"professionUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"parentUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"indicatorRatio\":0.3}]";
////
////        Gson gson = new Gson();
////
////        ArrayList<ProfessionUpdateJsonBean> list = gson.fromJson(json, new TypeToken<List<ProfessionUpdateJsonBean>>() {
////        }.getType() );
////        ArrayList<ProfessionIndicatorRelation> beanList = new ArrayList<>();
////
////        list.forEach(bean -> {
////            ProfessionIndicatorRelation relation = new ProfessionIndicatorRelation();
////            relation.setUid(bean.getUid());
////            relation.setProfessionUid(bean.getProfessionUid());
////            relation.setIndicatorUid(bean.getIndicatorUid());
////            relation.setIndicatorRatio(bean.getIndicatorRatio());
////            relation.setParentUid(bean.getParentUid());
////            service.add(relation);
////            beanList.add(relation);
////
////        });
////
////        service.addBatch(beanList);
//
//        service.findAllSon("4a41b9ae663d6d3c01663d6d508c0000","4a41b9ae663d6d3c01663d6d508c0000");
//
//    }
//}
