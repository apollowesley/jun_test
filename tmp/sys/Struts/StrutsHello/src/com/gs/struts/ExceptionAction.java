package com.gs.struts;

/**
 * Created by WangGenshen on 2/29/16.
 */
public class ExceptionAction {

    public String execute() {
        int result = 10 / 0;
        return "success";
    }

}
