package com.jfast.web.admin;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


// @SpringBootTest
public class AdminApplicationTests {

    @Test
    public void contextLoads() {

        Set<String> a = new HashSet<String>();
        a.add("sui yue weu ");
        a.add("guang hui sui yue ");
        a.add("hai kuo tian kong");
      //  Collections.reverse(a);// 将ArrayLista中的元素进行倒序
     //   Collections.
        for (String str : a) {
            System.out.println(str);
        }
    }

}
