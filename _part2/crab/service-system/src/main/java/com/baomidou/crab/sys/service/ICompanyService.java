package com.baomidou.crab.sys.service;

import java.util.List;

import com.baomidou.crab.sys.entity.Company;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 系统公司表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-10-21
 */
public interface ICompanyService extends IService<Company> {


    /**
     * <p>
     * 查询公司信息分页
     * </p>
     *
     * @param page    分页对象
     * @param company 公司信息
     * @return
     */
    IPage<Company> page(Page page, Company company);


    /**
     * <p>
     * 有效的企业列表
     * </p>
     */
    List<Company> listEffective();


    /**
     * <p>
     * 更新用户状态
     * </p>
     *
     * @param id     用户 ID
     * @param status 状态
     * @return
     */
    boolean updateStatus(Long id, Integer status);
}
