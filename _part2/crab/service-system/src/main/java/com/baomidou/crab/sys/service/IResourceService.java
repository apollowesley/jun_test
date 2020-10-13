package com.baomidou.crab.sys.service;

import java.util.List;

import com.baomidou.crab.sys.entity.Resource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-09-13
 */
public interface IResourceService extends IService<Resource> {


    /**
     * <p>
     * 资源分页
     * </p>
     *
     * @param page     分页对象
     * @param resource 资源对象
     * @return
     */
    IPage<Resource> page(Page page, Resource resource);

    /**
     * <p>
     * 查询指定用户资源列表
     * </p>
     *
     * @param userId 用户 ID
     * @return
     */
    List<Resource> listMenuByUserId(Long userId);

    /**
     * <p>
     * 有效的企业列表
     * </p>
     */
    List<Resource> listEffective();

    /**
     * <p>
     * 修改状态
     * </p>
     *
     * @param id     主键 ID
     * @param status 状态
     * @return
     */
    boolean changeStatus(Long id, Integer status);
}