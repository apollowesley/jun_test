package org.iartisan.admin.template.service.management;

import org.iartisan.admin.template.dao.mapper.BizDeptMapper;
import org.iartisan.admin.template.dao.model.BizDeptDO;
import org.iartisan.admin.template.service.entity.DeptEntity;
import org.iartisan.admin.template.service.query.DeptQueryService;
import org.iartisan.runtime.constants.DataStatus;
import org.iartisan.runtime.exception.NotAllowedException;
import org.iartisan.runtime.support.BaseManagementServiceSupport;
import org.iartisan.runtime.utils.StringUtils;
import org.iartisan.runtime.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门管理
 *
 * @author King  2019-7-19
 */
@Service
public class DeptManagementService extends BaseManagementServiceSupport<BizDeptMapper, DeptEntity> {

    @Autowired
    private DeptQueryService deptQueryService;

    /**
     * 添加部门
     *
     * @param entity
     */
    public void addData(DeptEntity entity) {
        //获取path
        DeptEntity dbResult = deptQueryService.getDataById(entity.getDeptParent());
        String deptPath = dbResult.getDeptPath();
        BizDeptDO dbInsert = new BizDeptDO();
        //生成uuid
        String uuid = UUIDUtil.shortId();
        String path = new StringBuffer(deptPath).append("-").append(uuid).toString();
        dbInsert.setDeptId(uuid);
        dbInsert.setDeptName(entity.getDeptName());
        dbInsert.setDeptPath(path);
        dbInsert.setDeptParent(entity.getDeptParent());
        dbInsert.setStatus(DataStatus.E.name());
        dbInsert.setCreateTime(new Date());
        dbInsert.setUpdateTime(new Date());
        this.baseMapper.insert(dbInsert);
    }

    public void deleteData(Serializable id) throws NotAllowedException {
        //root节点不允许删除
        DeptEntity dbResult = deptQueryService.getDataById(id);
        if (StringUtils.isEmpty(dbResult.getDeptParent())) {
            throw new NotAllowedException("一级节点不允许删除");
        }
        super.deleteDataById(id);
    }

}
