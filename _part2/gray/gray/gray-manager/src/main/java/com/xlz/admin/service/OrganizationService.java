package com.xlz.admin.service;

import java.util.List;

import com.xlz.admin.model.Organization;
import com.xlz.admin.model.Tree;
import com.xlz.commons.base.service.BaseService;

/**
 *
 * Organization 表数据服务层接口
 *
 */
public interface OrganizationService extends BaseService<Organization> {

    List<Tree> selectTree();

    List<Organization> selectTreeGrid();

}