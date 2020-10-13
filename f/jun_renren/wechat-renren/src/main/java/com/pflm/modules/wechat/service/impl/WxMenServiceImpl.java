package com.pflm.modules.wechat.service.impl;
import com.pflm.api.WeChatApi;
import com.pflm.modules.wechat.dao.WxMenuDao;
import com.pflm.modules.wechat.entity.WxMenuEntity;
import com.pflm.modules.wechat.service.WxMenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 自定义菜单
 * @author qinxuewu
 * @version 1.00
 * @time 16/11/2018下午 2:59
 */
@Service
public class WxMenServiceImpl implements WxMenService {

    @Autowired
    WxMenuDao wxMenuDao;

    @Override
    public WxMenuEntity get() {
        return wxMenuDao.get();
    }

    @Override
    public void save(WxMenuEntity menu) {
        wxMenuDao.save(menu);
    }
}
