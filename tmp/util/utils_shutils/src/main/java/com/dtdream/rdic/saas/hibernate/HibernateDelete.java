package com.dtdream.rdic.saas.hibernate;

import com.dtdream.rdic.saas.def.Constant;
import com.dtdream.rdic.saas.po.PoBasic;

/**
 * Created by Ozz8 on 2015/06/15.
 */
public class HibernateDelete implements HibernateUpdater<PoBasic> {
    @Override
    public void update(PoBasic po, int index, Object... objects) {
        po.setIsdel(Constant.YES);
    }
}
