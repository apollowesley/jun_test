package org.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangcai_zhu
 * @Description TODO
 * @Date 2020/6/9 10:38
 *
 *  -Xmx1024m -Xms512m -Xmn384m
 */
@RestController
public class JvmController {

  @RequestMapping(value = "/hello")
  public String hello() {
    List<byte[]> list = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      list.add(new byte[1024 * 1024 * 200]);
      System.out.println("use " + 200 * (i + 1) + " M memory");
      //输出800m后 抛出异常 java.lang.OutOfMemoryError: Java heap space
    }
    return "hello";
  }

}
