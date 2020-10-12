package com.kind.samples.patterns.proxy;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public interface DAO {
    public static final int save = 1;
    public static final int remove = 2;
    public static final int change = 3;
    public static final int get = 4;

    void save();

    void remove();

    void change();

    int get();

}
