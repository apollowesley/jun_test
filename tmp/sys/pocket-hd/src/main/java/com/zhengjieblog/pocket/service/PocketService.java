package com.zhengjieblog.pocket.service;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.entity.PocketDetail;
import com.zhengjieblog.pocket.repo.spec.PocketSpec;
import com.zhengjieblog.pocket.util.ResponseData;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
public interface PocketService {

    /**
     * 根据年月日查找记账记录
     * @param time
     * @return
     */
    Pocket getPocketByYearAndMonthAndDay(Date time);

    /**
     * 保存或更新记录
     * @param pocket
     */
    void save(Pocket pocket);

    /**
     * 带分页的查询
     * @param pocketSpec
     * @param pageable
     * @return
     */
    List<Pocket> find(PocketSpec pocketSpec, Pageable pageable);

    /**
     * 不带分页的查询
     * @param pocketSpec
     * @return
     */
    ResponseData findDetail(PocketSpec pocketSpec);

    /**
     * 检查并删除记账记录
     * @param userID
     */
    void check(Long userID);

    /**
     * 删除记账详情后更新关联表数据
     * @param oldPocket
     * @param oldPocketDetail
     */
    void updatePocket(Pocket oldPocket, PocketDetail oldPocketDetail);
}
