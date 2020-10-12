package com.kind.samples.patterns.proxy;

import com.kind.samples.patterns.proxy.impl.Check;
import com.kind.samples.patterns.proxy.impl.DAOImplProxy;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class ProxyClient {
    public void test() {
        Check check = new Check(DAO.get);
        this.testProxy(check);
        check = new Check(DAO.save);
        this.testProxy(check);
        check = new Check(DAO.change);
        this.testProxy(check);
    }

    private void testProxy(Check check) {
        DAOImplProxy proxy = new DAOImplProxy(check);
        proxy.save();
        proxy.remove();
        proxy.change();
        proxy.get();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new ProxyClient().test();
    }
}
