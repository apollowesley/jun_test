package org.iartisan.admin.template.service.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.iartisan.admin.template.authentication.service.entity.ZTreeEntity;
import org.iartisan.admin.template.dao.mapper.BizDeptMapper;
import org.iartisan.admin.template.dao.model.BizDeptDO;
import org.iartisan.admin.template.service.entity.DeptEntity;
import org.iartisan.runtime.support.BaseQueryServiceSupport;
import org.iartisan.runtime.utils.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理
 *
 * @author King  2019-7-19
 */
@Service
public class DeptQueryService extends BaseQueryServiceSupport<BizDeptMapper, DeptEntity> {


    public List<ZTreeEntity> getDeptList() {
        BizDeptDO rootQuery = new BizDeptDO();
        rootQuery.setDeptId("1");
        //获取root节点
        Wrapper<BizDeptDO> queryWrapper = new QueryWrapper<>(rootQuery);
        List<BizDeptDO> dbResult = this.baseMapper.selectList(queryWrapper);
        List<ZTreeEntity> dataList = new ArrayList<>();
        ZTreeEntity root = new ZTreeEntity();
        if (CollectionUtil.isNotEmpty(dbResult)) {
            BizDeptDO rootDept = dbResult.get(0);
            root.setId(rootDept.getDeptId());
            root.setName(rootDept.getDeptName());
            dataList.add(root);
            //查询下级节点
            getChildrenTree(root, dataList);
        }
        return dataList;
    }

    private void getChildrenTree(ZTreeEntity entity, List<ZTreeEntity> dataList) {
        //查询下级节点
        List<ZTreeEntity> childrenDept = getChildrenDept(entity.getId());
        if (CollectionUtil.isNotEmpty(childrenDept)) {
            for (ZTreeEntity o : childrenDept) {
                o.setpId(entity.getId());
                dataList.add(o);
                getChildrenTree(o, dataList);
            }
        }
    }

    private List<ZTreeEntity> getChildrenDept(String deptId) {
        BizDeptDO dbQuery = new BizDeptDO();
        dbQuery.setDeptParent(deptId);
        Wrapper<BizDeptDO> queryWrapper = new QueryWrapper<>(dbQuery);
        List<BizDeptDO> dbResult = this.baseMapper.selectList(queryWrapper);
        List<ZTreeEntity> dataList = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(dbResult)) {
            dbResult.forEach(v -> {
                ZTreeEntity entity = new ZTreeEntity();
                entity.setName(v.getDeptName());
                entity.setId(v.getDeptId());
                entity.setpId(deptId);
                dataList.add(entity);
            });
            return dataList;
        }
        return null;
    }




}
