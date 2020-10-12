//package com.guest.assessment;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.guest.assessment.emus.SysConf;
//import com.guest.assessment.entity.IndicatorGradeReference;
//import com.guest.assessment.entity.ProfessionAddJsonBean;
//import com.guest.assessment.service.IndicatorGradeReferenceService;
//import com.guest.assessment.utils.ResultUtil;
//import org.json.JSONException;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class indicatorGradeReferenceTest extends AssessApplicationTests {
//
//    @Autowired
//    IndicatorGradeReferenceService   service;
//    @Test
//    public void Test() throws JSONException {
////        IndicatorGradeReference bean = new IndicatorGradeReference();
////        bean.setName("高考入学平均分550~600");
////        bean.setDescription("每名学生高考总分");
////        bean.setIndicatorUid("4a41b9ae6642a33e016642a357ee0000");
////        bean.setGrade(100.00);
////        service.add(bean);
//
////        IndicatorGradeReference bean = service.findByName("高考入学平均分450~500");
//////        service.delete(bean);
////        bean.setGrade(59.00);
////        service.update(bean);
//
////        List<IndicatorGradeReference> list = service.getList();
////        for (IndicatorGradeReference bean:
////             list) {
////            System.out.println(bean.getName());
////        }
//
////        String json = "[{\"indicatorUid\":\"4a41b9ae663e60a101663e60b4f20000\",\"professionUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"parentUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"indicatorRatio\":0.5},{\"indicatorUid\":\"4a41b9ae66484a4f0166484c8bbc0000\",\"professionUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"parentUid\":\"4a41b9ae663d6d3c01663d6d508c0000\",\"indicatorRatio\":0.5}]";
//
////        JSONArray jsonArray = new JSONArray(json);
////        ArrayList<ProfessionIndicatorRelation> list = new ArrayList<>();
////        double ratio = 0.0;
////        for (int i = 0; i <jsonArray.length(); i++) {
////            JSONObject jsonObject =  jsonArray.getJSONObject(i);
////            ProfessionIndicatorRelation bean = new ProfessionIndicatorRelation();
////            bean.setIndicatorUid(jsonObject.getString("indicatorUid"));
////            bean.setProfessionUid(jsonObject.getString("professionUid"));
////            bean.setParentUid(jsonObject.getString("parentUid"));
////
////            bean.setIndicatorRatio(jsonObject.getDouble("indicatorRatio"));
////
////            ratio += jsonObject.getDouble("indicatorRatio");
////
////            Iterator keys = jsonObject.keys();
////
////
////            while (keys.hasNext()){
////                if (keys.next().equals("uid")){
////                    bean.setUid(jsonObject.getString("uid"));
////                }
////            }
////            list.add(bean);
////        }
////
////        System.out.println(ratio);
//
////        Gson gson = new Gson();
////        //ArrayList<ProfessionAddJsonBean> list = new ArrayList<ProfessionAddJsonBean>();
////
////        ArrayList<ProfessionAddJsonBean> list= gson.fromJson(json, new TypeToken<List<ProfessionAddJsonBean>>() {
////        }.getType() );
////
////        for (ProfessionAddJsonBean bean:
////             list) {
////            System.out.println(bean.getProfessionUid());
////        }
//
//
////        Page<IndicatorGradeReference> list = service.findPageCriteria(0, 10, new IndicatorGradeReference());
////
////        System.out.println(ResultUtil.result(SysConf.SUCCESS, list));
//
//
//    }
//}
