package com.xxx;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 用来测试<context:annotation-config>和<context:component-scan>
 * @author hztaoran
 */

@Component
public class B {
    public B() {
        System.out.println("creating bean B: " + this);
    }

}
