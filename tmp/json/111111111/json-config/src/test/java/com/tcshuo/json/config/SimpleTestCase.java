/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;

/**
 *
 * @author tengda
 */
public class SimpleTestCase extends TestCase {

    public void test1() throws Exception {

        
        
        
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("maptype", BeanType.A);
        Bean beana = new Bean("bean a", BeanType.A);
        Bean beanb = new Bean("bean b", BeanType.B);
        map.put("beana", beana);
        map.put("beanb", beanb);

        ArrayList<Bean> beanList = new ArrayList<Bean>();
        beanList.add(beana);
        beanList.add(beanb);
        map.put("beanList", beanList);

        int[] intArray = new int[]{1, 2, 3};
        map.put("intArray", intArray);

        Bean[] beanArray = new Bean[]{beana, beanb};
        map.put("beanArray", beanArray);
        
        
        

     
        toJson(null, map);
        toJson("{ignore:'n'}", map);
        toJson("{ignore:'n'}", beanList);
        toJson("{ignore:'na',allows:['name','date'],childs:{date:{format:'yyyy年MM月'}}}", beanList);
        toJson("{ignore:'na',allows:['name','type'],childs:{type:{ignore:'na',allows:['javaEnumName']}}}", beanList);
        toJson("{ignore:'na',allows:['name','type'],childs:{type:{alias:{javaEnumName:'NAME'},ignore:'na',allows:['javaEnumName']}}}", beanList);

        
        
        
    }

    private void toJson(String config, Object target) {
        System.out.println(JsonConfigParser.parser(config, target).toJson());
    }

}
