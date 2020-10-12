package com.kind.samples.patterns.proxy.impl;

import com.kind.samples.patterns.proxy.DAO;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class DAOImplProxy implements DAO {
    private Check check;
    private DAO dao;

    public DAOImplProxy(Check check) {
        this.check = check;
        this.dao = new DAOImpl();
    }

    @Override
    public void save() {
        System.out.println("after do save**************");
        if (this.check.getLevel() >= DAO.save) {
            System.out.println("Access authorized, calling real save.");
            this.dao.save();
        } else {
            System.out.println("Access denied, because current permission not allowed.");
        }
    }

    @Override
    public void remove() {
        System.out.println("after do remove**************");
        if (this.check.getLevel() >= DAO.remove) {
            System.out.println("Access authorized,calling real remove.");
            this.dao.remove();
        } else {
            System.out.println("Access denied,because current permission not allowed.");
        }
    }

    @Override
    public void change() {
        System.out.println("after do change**************");
        if (this.check.getLevel() >= DAO.change) {
            System.out.println("Access authorized,calling real change.");
            this.dao.change();
        } else {
            System.out.println("Access denied,because current permission not allowed.");
        }
    }

    @Override
    public int get() {
        System.out.println("after do get**************");
        if (this.check.getLevel() >= DAO.get) {
            System.out.println("Access authorized,calling real get.");
            return this.dao.get();
        } else {
            System.out.println("Access denied,because current permission not allowed.");
        }
        return 0;
    }
}
