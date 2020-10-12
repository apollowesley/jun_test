package cn.kiwipeach.ws.api.impl;

import cn.kiwipeach.ws.api.SecondWS;

/**
 * Create Date: 2017/11/14
 * Description:
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class SecondWSImpl implements SecondWS {

    @Override
    public String echoMsg(String msg) {
        return "echo==>"+msg;
    }
}
