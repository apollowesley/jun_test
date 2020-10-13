package com.baomidou.crab.sys.service;

import java.io.Serializable;
import java.util.List;

import com.baomidou.crab.sys.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统机构表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-11-07
 */
public interface IOrgService extends IService<Org> {

    /**
     * <p>
     * 查询指定企业 ID 机构列表
     * </p>
     *
     * @param companyId 企业 ID
     * @return
     */
    List<Org> listByCompanyId(Long companyId);


    /**
     * <p>
     * 查询 ID 子节点数
     * </p>
     *
     * @param id 主键 ID
     * @return
     */
    Integer childNode(Serializable id);
}
