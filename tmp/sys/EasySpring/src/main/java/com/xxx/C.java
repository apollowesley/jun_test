package com.xxx;

import org.springframework.stereotype.Component;

/**
 * 用来测试<context:annotation-config>和<context:component-scan>
 * @author hztaoran
 */

@Component
public class C {
    public C() {
        System.out.println("creating bean C: " + this);
    }
}
