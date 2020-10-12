package com.zhengjieblog.pocket.repo;

import com.zhengjieblog.pocket.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
public interface PocketRepo extends JpaRepository<Pocket,Long>,JpaSpecificationExecutor {
    /**
     * 根据年月日查找记账记录
     * @param year
     * @param month
     * @param day
     * @return
     */
    Pocket findByYearAndMonthAndDay(int year, int month, int day);
}
