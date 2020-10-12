package com.kind.samples.patterns.abstractfactory.impl;

import com.kind.samples.patterns.abstractfactory.AbstractFactory;
import com.kind.samples.patterns.abstractfactory.BenzCarFactory;
import com.kind.samples.patterns.abstractfactory.BmwCarFactory;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class BusinessCarFactory implements AbstractFactory {

    public BmwCarFactory createBmwCar() {
        return new BmwBusinessCar();
    }

    public BenzCarFactory createBenzCar() {
        return new BenzBusinessCar();
    }
}
