package com.baomidou.crab.config;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.crab.common.web.Account;
import com.baomidou.crab.common.web.LoginHelper;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 填充器
 * </p>
 *
 * @author jobob
 * @since 2018-11-01
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Account account = LoginHelper.getAccount(false);
        if (null != account) {
            setFieldValByName("operator", account.getName(), metaObject);
        }
        setFieldValByName("createTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}