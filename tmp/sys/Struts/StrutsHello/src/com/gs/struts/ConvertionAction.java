package com.gs.struts;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by WangGenshen on 3/31/16.
 *
 * 未实现ValidationAware接口:
 * 如果从表单提交数据过来,age输入的是一个正常的可以转化为数字的字符串,则age成功获取,
 * 如果age输入的是一个不能转化为数字的字符串，则age不能获取到，但struts当作什么都没发生，此时age使用默认值0
 *
 * 实现ValidationAware接口:
 * 如果从表单提交数据,age输入一个正常可以转化为数字的字符串,则age成功获取,
 * 如果age输入的是一个不能转化为数字的字符串,则struts会检查此action是否配置了一个result为input的result,
 * 如果配置了,则转发到input指向的目标url,否则抛出异常
 *
 * ActionSupport类实现了ValidationAware接口,所以自定义Action类可以继承自ActionSupoprt类
 */
public class ConvertionAction extends ActionSupport {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String execute() {
        return "success";
    }

    // TODO Struts还可以自定义类型转换器,如从页面中输入的时间字符串转化成java.util.Date类型
}
