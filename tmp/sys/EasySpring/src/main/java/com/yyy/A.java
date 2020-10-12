package com.yyy;

import com.xxx.B;
import com.xxx.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用来测试<context:annotation-config>和<context:component-scan>
 * @author hztaoran
 */

@Component
public class A {

    private B bbb;

    private C ccc;

    public A() {
        System.out.println("creating bean A: " + this);
    }

    @Autowired
    public void setBbb(B bbb) {
        System.out.println("setting A.bbb with " + bbb);
        this.bbb = bbb;
    }

    @Autowired
    public void setCcc(C ccc) {
        System.out.println("setting A.ccc with " + ccc);
        this.ccc = ccc;
    }
}