package com.gs.struts;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangGenshen on 3/4/16.
 */
public class FormTagAction {

    private FormUser formUser;
    private List<Integer> addresses;

    public FormUser getFormUser() {
        return formUser;
    }

    public void setFormUser(FormUser formUser) {
        this.formUser = formUser;
    }

    public List<Integer> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Integer> addresses) {
        this.addresses = addresses;
    }

    public String toLogin() {
        formUser = new FormUser();
        formUser.setName("form value stack"); // 该值会被回显到jsp页面所对应的属性中
        formUser.setAccept(true); // jsp页面中的accept复选框默认为选中状态
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1001, "a address"));
        addresses.add(new Address(1002, "b address"));
        formUser.setAddresses(addresses);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.push(formUser);
        return "success";
    }

    public String login() {
        System.out.println(formUser);
        System.out.println(addresses);
        return "success";
    }

}
