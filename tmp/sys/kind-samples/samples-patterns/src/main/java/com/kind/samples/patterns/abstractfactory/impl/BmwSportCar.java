package com.kind.samples.patterns.abstractfactory.impl;

import com.kind.samples.patterns.abstractfactory.BmwCarFactory;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class BmwSportCar implements BmwCarFactory {
    public String createBmwCar() {
        return "I am a bmw sport car*********";
    }
}
