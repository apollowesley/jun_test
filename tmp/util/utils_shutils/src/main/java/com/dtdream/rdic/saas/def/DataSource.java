package com.dtdream.rdic.saas.def;


import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;

import java.util.Map;

/**
 * Created by Ozz8 on 2015/07/16.
 */
public class DataSource extends DatasourceConnectionProviderImpl {
    @Override
    public void configure(Map configValues) {
        String username = (String) configValues.get(Environment.USER);
        String password = (String) configValues.get(Environment.PASS);
        username = "";
        password = "";
        configValues.put(Environment.USER, username);
        configValues.put(Environment.PASS, password);
        super.configure(configValues);
    }
}
