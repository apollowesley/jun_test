package cn.kiwipeach.design.structure.proxy.normal;

import cn.kiwipeach.design.structure.proxy.normal.impl.TargetDaoImpl;
import cn.kiwipeach.design.structure.proxy.normal.proxy.DaoProxy;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/11/19
 **/
public class RunMain {

    public static void main(String[] args) {
        TargetDao targetDao = new TargetDaoImpl();
        DaoProxy daoProxy = new DaoProxy();
        daoProxy.save(10);
    }
}
