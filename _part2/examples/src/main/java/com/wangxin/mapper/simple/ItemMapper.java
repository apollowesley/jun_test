package com.wangxin.mapper.simple;

import java.util.List;

import com.wangxin.entity.simple.Item;
import com.wangxin.mapper.BaseMapper;

/** 
 * @Description 新闻mapper接口
 * @author 王鑫 
 * @date Mar 16, 2017 3:35:19 PM  
 */
public interface ItemMapper extends BaseMapper<String, Item> {

    List<Item> findItemByPage();

}
