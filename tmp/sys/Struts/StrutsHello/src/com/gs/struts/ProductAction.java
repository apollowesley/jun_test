package com.gs.struts;

/**
 * Created by WangGenshen on 2/26/16.
 *
 * input.jsp页面中input的name应该为product.id的形式
 * 在details.jsp页面中获取值用${product.id}的形式
 */
public class ProductAction {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String execute() {
        product.setId(1);
        System.out.println(product);
        return "details";
    }

    /**
     * 如果使用save方法,则需要在struts.xml配置文件的action中指定method=save
     * @return
     */
    public String save() {
        product.setId(1);
        System.out.println(product);
        return "details";
    }
}
