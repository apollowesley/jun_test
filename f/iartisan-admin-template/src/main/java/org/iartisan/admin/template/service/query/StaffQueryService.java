package org.iartisan.admin.template.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.iartisan.admin.template.dao.mapper.BizStaffMapper;
import org.iartisan.admin.template.dao.model.BizStaffDO;
import org.iartisan.admin.template.service.entity.DeptEntity;
import org.iartisan.admin.template.service.entity.StaffEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.constants.DataStatus;
import org.iartisan.runtime.constants.FlagType;
import org.iartisan.runtime.exception.NoRecordException;
import org.iartisan.runtime.support.BaseQueryServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门管理
 *
 * @author King  2019-7-19
 */
@Service
public class StaffQueryService extends BaseQueryServiceSupport<BizStaffMapper, StaffEntity> {

    @Autowired
    private DeptQueryService deptQueryService;

    /**
     * 分页查询员工数据
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper<StaffEntity> getPageData(Pagination page, StaffEntity entity) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("STAFF_DEPT", entity.getStaffDept());
        wrapper.eq("STATUS", DataStatus.E.name());
        return getPageDataByWrapper(page, wrapper);
    }

    public List<StaffEntity> getStaffListByDeptId(String deptId) {
        StaffEntity t = new StaffEntity();
        t.setStaffDept(deptId);
        t.setStaffStatus(FlagType.Y.name());
        return getListDataByObjs(t);
    }

    /**
     * 获取部门领导
     *
     * @param staffId
     * @return
     */
    public StaffEntity getDeptLeader(String staffId) throws NoRecordException {
        BizStaffDO dbStaffResult = this.baseMapper.selectById(staffId);
        if (null != dbStaffResult) {
            DeptEntity dbDeptResult = deptQueryService.getDataById(dbStaffResult.getStaffDept());
            BizStaffDO dbLeaderStaff = this.baseMapper.selectById(dbDeptResult.getDeptLeader());
            if (null != dbLeaderStaff) {
                StaffEntity entity = new StaffEntity();
                entity.setStaffName(dbLeaderStaff.getStaffName());
                entity.setStaffId(dbLeaderStaff.getStaffId());
                return entity;
            }
        }
        throw new NoRecordException("没有查到相应的部门领导");
    }

}
