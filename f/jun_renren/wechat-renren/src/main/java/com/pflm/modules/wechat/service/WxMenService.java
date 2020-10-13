package com.pflm.modules.wechat.service;

import com.pflm.modules.wechat.entity.WxMenuEntity;


public interface WxMenService {

    WxMenuEntity get();

    void save(WxMenuEntity menu);
}
