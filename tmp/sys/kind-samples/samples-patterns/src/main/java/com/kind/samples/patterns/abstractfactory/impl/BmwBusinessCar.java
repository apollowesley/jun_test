package com.kind.samples.patterns.abstractfactory.impl;

import com.kind.samples.patterns.abstractfactory.BmwCarFactory;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class BmwBusinessCar implements BmwCarFactory {
    @Override
    public String createBmwCar() {
        return "I am a bmw business car*******";
    }
}
