package com.dtdream.rdic.saas.intf;

/**
 * Created by Ozz8 on 2015/07/04.
 */
public abstract class Worker<R,T> {
    public abstract R work (T object);
}
