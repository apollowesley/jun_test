package com.dtdream.rdic.saas.hibernate;

import org.hibernate.type.Type;

/**
 * Created by Ozz8 on 2015/07/18.
 */
public class HibernateSelector {
    public String select;
    public String as;
    public Type scalar;

    public HibernateSelector() {
    }

    public HibernateSelector(String select, String as, Type scalar) {
        this.select = select;
        this.as = as;
        this.scalar = scalar;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public Type getScalar() {
        return scalar;
    }

    public void setScalar(Type scalar) {
        this.scalar = scalar;
    }
}
