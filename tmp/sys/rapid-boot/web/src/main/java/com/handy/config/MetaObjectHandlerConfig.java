package com.handy.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.handy.common.constants.Constants;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author handy
 * @Description: {配置字段默认规则}
 * @date 2019/8/22 17:11
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //默认未删除
        setFieldValByName("isDeleted", Constants.NOT_DELETE_FLAG, metaObject);
        //创建时间默认当前时间
        setFieldValByName("createTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //修改时间
        setFieldValByName("modifyTime", new Date(), metaObject);
    }
}
