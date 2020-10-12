package com.dtdream.rdic.saas.def;

import java.util.List;

/**
 * Created by Ozz8 on 2015/07/04.
 */
public class Counter<T> {
    long count;
    T sum;
    List<T> values;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public T getSum() {
        return sum;
    }

    public void setSum(T sum) {
        this.sum = sum;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
