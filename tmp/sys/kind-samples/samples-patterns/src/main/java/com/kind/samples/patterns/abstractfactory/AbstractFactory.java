package com.kind.samples.patterns.abstractfactory;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public interface AbstractFactory {

    /**
     * 创建宝马
     *
     * @return
     */
    BmwCarFactory createBmwCar();

    /**
     * 创建奔驰
     *
     * @return
     */
    BenzCarFactory createBenzCar();
}
