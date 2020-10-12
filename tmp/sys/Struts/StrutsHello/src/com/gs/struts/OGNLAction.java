package com.gs.struts;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by WangGenshen on 2/27/16.
 */
public class OGNLAction implements RequestAware, SessionAware, ApplicationAware {

    private Product product;
    private String result;

    private Map<String, Object> request;
    private Map<String, Object> session;
    private Map<String, Object> application;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String execute() {
        product = new Product();
        product.setId(100);
        product.setName("product");
        result = "result";

        // 获取ValueStack,并手动在栈顶压入对象,此时在JSP页面中直接使用该对象的属性名获取值
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        Product newProduct = new Product();
        newProduct.setId(200);
        newProduct.setName("new product");
        valueStack.push(newProduct);

        application.put("applicationKey", "applicationValue");
        session.put("sessionKey", "sessionValue");
        request.put("requestKey", "requestValue");

        return "success";
    }

    @Override
    public void setApplication(Map<String, Object> map) {
        this.application = map;
    }

    @Override
    public void setRequest(Map<String, Object> map) {
        this.request = map;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    public static String test(String param) {
        return "static method: " + param;
    }
}
