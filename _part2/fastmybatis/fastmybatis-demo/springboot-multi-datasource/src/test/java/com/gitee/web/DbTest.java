package com.gitee.web;

import com.gitee.web.stu.entity.TUser;
import com.gitee.web.stu.mapper.TUserMapper;
import com.gitee.web.stu2.entity.Goods;
import com.gitee.web.stu2.mapper.GoodsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author tanghc
 */
public class DbTest extends SpringbootMultiDatasourceApplicationTests {

    @Autowired
    TUserMapper tUserMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    public void get() {
        TUser tUser = tUserMapper.getById(2);
        Assert.notNull(tUser, "tUser can not null");

        Goods goods = goodsMapper.getById(1);
        Assert.notNull(goods, "goods can not null");

        tUser = tUserMapper.selectByName("张三");
        Assert.notNull(tUser, "select tUser can not null");

        goods = goodsMapper.selectByName("iPhoneX");
        Assert.notNull(goods, "select goods can not null");
    }

}
