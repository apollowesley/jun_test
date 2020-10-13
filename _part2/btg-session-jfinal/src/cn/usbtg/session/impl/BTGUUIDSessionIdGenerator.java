package cn.usbtg.session.impl;

import cn.usbtg.session.BTGSessionIdGenerator;

import java.util.UUID;

/**
 * 默认session id生成器：采用uuid
 */
public class BTGUUIDSessionIdGenerator implements BTGSessionIdGenerator {

    @Override
    public String genaeratorSessionId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}
