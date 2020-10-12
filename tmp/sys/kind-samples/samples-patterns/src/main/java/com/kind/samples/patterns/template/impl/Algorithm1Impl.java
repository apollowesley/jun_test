package com.kind.samples.patterns.template.impl;

import com.kind.samples.patterns.template.interfaces.Algorithm1;

public class Algorithm1Impl extends Algorithm1 {

    @Override
    public void calc1() {
        System.out.println("doing calc1 in Algorithm1Impl");
    }

    @Override
    public void calc2() {
        System.out.println("doing calc2 in Algorithm1Impl");
    }

    @Override
    public void calc3() {
        System.out.println("doing calc3 in Algorithm1Impl");
    }

    public void showResult() {
        System.out.println("show result is implemented in Algorithm1Impl");
    }

}
