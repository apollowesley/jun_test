package com.gs.struts;

/**
 * Created by WangGenshen on 2/29/16.
 */
public class GlobalExceptionAction {

    public String execute() {
        String s = null;
        if (s.equals("s")) {
            System.out.println("String s equals 's'");
        }
        return "success";
    }

}
