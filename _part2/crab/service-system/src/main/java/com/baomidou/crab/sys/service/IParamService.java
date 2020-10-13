package com.baomidou.crab.sys.service;

import com.baomidou.crab.sys.entity.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
public interface IParamService extends IService<Param> {


    /**
     * <p>
     * 查询参数信息分页
     * </p>
     *
     * @param page  分页对象
     * @param param 参数信息
     * @return
     */
    IPage<Param> page(Page page, Param param);


    /**
     * <p>
     * 更新系统参数值
     * </p>
     *
     * @param id  用户 ID
     * @param sys 系统参数 0、否 1、是
     * @return
     */
    boolean updateSys(Long id, Integer sys);
}
