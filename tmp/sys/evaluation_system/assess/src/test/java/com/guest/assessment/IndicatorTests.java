//package com.guest.assessment;
//
//import com.guest.assessment.emus.EStatus;
//import com.guest.assessment.entity.Indicator;
//import com.guest.assessment.service.IndicatorService;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class IndicatorTests extends AssessApplicationTests {
//
//    @Autowired
//    IndicatorService service;
//    @Test
//    public void Test(){
////        Indicator indicator = new Indicator();
////        indicator.setName("高考录取学生第一志愿录取率");
////        indicator.setTypeStatus(1);
////        service.add(indicator);
//
////        Indicator bean = service.findByName("生源情况");
////        bean.setStatus(EStatus.DISABLED);
////        service.delete(bean);
//
////        Indicator bean = service.findByName("生源情况");
////        bean.setName("生源情况1");
////        service.update(bean);
//
////        List<Indicator> list = service.getList();
////
////        for (Indicator bean:
////             list) {
////            System.out.println(bean.getName());
////        }
//
////        Page<Indicator> pageByTypeStatus = service.findPageByTypeStatus(2, 2, 0);
////
////        Iterator<Indicator> iterator = pageByTypeStatus.iterator();
////
////        while (iterator.hasNext()){
////            System.out.println(iterator.next().toString());
////        }
//        ArrayList<String> list = new ArrayList<>();
//        list.add("4a41b9ae663e60a101663e60b4f20000");
//        list.add("4a41b9ae663e60a101663e60b4f20000");
//
//        List<Indicator> indicators = service.finaAllByUids(list);
//
//        indicators.forEach( item -> System.out.println(item.getName()));
//
//    }
//}
