package com.zhengjieblog.pocket.service;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.entity.PocketDetail;
import com.zhengjieblog.pocket.entity.User;
import com.zhengjieblog.pocket.util.ResponseData;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
public interface PocketDetailService {
    /**
     * 创建一条详细记录
     * @param user
     * @param pocket
     * @param pocketDetail
     * @return
     */
    Pocket create(User user, Pocket pocket, PocketDetail pocketDetail);

    /**
     * 删除
     * @param id
     * @return
     */
    ResponseData delete(Long id);

    /**
     * 根据id查询单个记录
     * @param id
     * @return
     */
    ResponseData info(Long id);


    /**
     * 编辑
     * @param pocket
     * @param detail
     * @param pocketDetail
     * @return
     */
    Pocket edit(Pocket pocket, PocketDetail detail, PocketDetail pocketDetail);

    /**
     * 根据id 获取记账详细信息
     * @param id
     * @return
     */
    PocketDetail getPocketDetail(Long id);
}
