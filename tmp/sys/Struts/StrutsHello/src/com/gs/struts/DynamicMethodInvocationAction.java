package com.gs.struts;

/**
 * Created by WangGenshen on 2/26/16.
 */
public class DynamicMethodInvocationAction {

    public String save() {
        System.out.println("save...");
        return "success";
    }

    public String update() {
        System.out.println("update...");
        return "success";
    }

}
