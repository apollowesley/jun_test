package com.gs.struts;

import java.util.*;

/**
 * Created by WangGenshen on 2/29/16.
 */
public class TagAction {

    private int price;
    private Date date;

    private List<Product> products;

    private ProductComparator productComparator;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ProductComparator getProductComparator() {
        return productComparator;
    }

    public String execute() {
        price = 1000;
        products = new ArrayList<>();
        products.add(new Product(1001, "b name1"));
        products.add(new Product(1002, "a name2"));
        productComparator = new ProductComparator();
        date = Calendar.getInstance().getTime();
        return "success";
    }

    class ProductComparator implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}
