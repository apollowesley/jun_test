package com.kind.samples.patterns.proxy.impl;

import com.kind.samples.patterns.proxy.DAO;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class DAOImpl implements DAO {
    @Override
    public void save() {
        System.out.println("do save…………");

    }

    @Override
    public void remove() {
        System.out.println("do remove…………");
    }

    @Override
    public void change() {
        System.out.println("do change…………");
    }

    @Override
    public int get() {
        System.out.println("do get");
        return 1;
    }
}
