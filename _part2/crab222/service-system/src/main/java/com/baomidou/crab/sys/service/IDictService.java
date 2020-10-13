package com.baomidou.crab.sys.service;

import java.util.List;

import com.baomidou.crab.sys.entity.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
public interface IDictService extends IService<Dict> {


    /**
     * <p>
     * 查询字典信息分页
     * </p>
     *
     * @param page 分页对象
     * @param dict 字典信息
     * @return
     */
    IPage<Dict> page(Page page, Dict dict);

    /**
     * <p>
     * 根据父字典编码查询字典列表
     * </p>
     *
     * @param parentCode 父字典编码
     * @return
     */
    List<Dict> listByParentCode(String parentCode);

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

    /**
     * <p>
     * 更新字典状态
     * </p>
     *
     * @param id     用户 ID
     * @param status 状态 -1、禁用 0、正常
     * @return
     */
    boolean updateStatus(Long id, Integer status);
}
