package com.gitee.web.stu2.mapper;

import com.gitee.fastmybatis.core.mapper.CrudMapper;

import com.gitee.web.stu.entity.TUser;
import com.gitee.web.stu2.entity.Goods;
import org.apache.ibatis.annotations.Param;


/**
 * @author tanghc
 */
public interface GoodsMapper extends CrudMapper<Goods, Integer> {
    Goods selectByName(@Param("goodsName") String goodsName);
}
