/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import com.tcshuo.json.node.Node;
import com.tcshuo.json.object.Formatter;
import com.tcshuo.json.object.JavaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author tengda
 */
public class ToObjectTestCase extends TestCase {

    public void test1() throws Exception {

        Bean beana = new Bean("bean a", BeanType.A);
        beana.setTargets(new Integer[]{1, 2, 3});
        beana.getUsers().add(new User("u1", "p1"));
        beana.getUsers().add(new User("u2", "p3"));

        Bean beanb = new Bean("bean b", BeanType.B);

        ArrayList<Bean> beanList = new ArrayList<Bean>();
        beanList.add(beana);
        beanList.add(beanb);

        toJson(null, beanList);
        // String json = toJson("{ignore:'n'}", beanList);
        String json = toJson("{ignore:'na',allows:['name','date'],childs:{date:{format:'yyyy年MM月'}}}", beanList);
       
        Node node =  Node.formJson(json);
        node.getChildNode("date").setFormatter(new Formatter() {

            public Object format(Node node) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
                    return format.parse(node.toString());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        
        Object result = node.toObject(new JavaType(ArrayList.class, Bean.class));
        //  Object result = node.toObject(new JavaType(Bean[].class, Bean.class));
        //  Object result = node.toObject(new JavaType(Bean[].class));
         //   Object result = node.toObject(Bean[].class);
        System.out.println(result);

    }

    private String toJson(String config, Object target) {
        String code = JsonConfigParser.parser(config, target).toJson();
        System.out.println(code);
        return code;
    }

}
