package com.kind.samples.patterns.abstractfactory.impl;

import com.kind.samples.patterns.abstractfactory.BenzCarFactory;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class BenzSportCar implements BenzCarFactory {
    @Override
    public String createBenzCar() {
        return "I am a benz sport car***********";
    }
}
