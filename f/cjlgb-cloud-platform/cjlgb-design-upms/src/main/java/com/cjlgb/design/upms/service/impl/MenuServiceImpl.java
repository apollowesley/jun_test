package com.cjlgb.design.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.impl.BaseServiceImpl;
import com.cjlgb.design.common.upms.entity.SysMenu;
import com.cjlgb.design.upms.mapper.SysMenuMapper;
import com.cjlgb.design.upms.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/19
 * description:
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements MenuService {


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void create(SysMenu params) {
        //  生成主键
        params.setId(IdWorker.getId());
        //  初始化索引字段
        StringBuilder builder = new StringBuilder();
        if (null == params.getPid() || params.getPid().equals(0L)){
            params.setPid(0L);
            builder.append(params.getId());
        } else {
            //  获取父节点
            SysMenu parent = this.getById(params.getPid());
            Assert.notNull(parent,"Pid不存在");
            builder.append(parent.getIndexes()).append(params.getId());
        }
        params.setIndexes(builder.append("/").toString());
        //  设置排序值
        if (null == params.getSort()){
            Integer maxSort = this.baseMapper.selectMaxSort(params.getPid());
            maxSort = null == maxSort ? 1 : ++ maxSort;
            params.setSort(maxSort);
        } else {
            //  重置排序值
            this.resetSort(params.getId(),params.getPid(),params.getSort(),1);
        }
        //  保存菜单
        this.save(params);
    }

    /**
     * 重置某节点下的所有排序值
     * @param ignoreId 需要忽略的Id
     * @param pid 父级Id
     * @param sort 排序值
     * @param offset 偏移量
     */
    private void resetSort(long ignoreId,long pid,int sort,int offset){
        //  获取同级菜单列表
        List<SysMenu> list = this.list(Wrappers.<SysMenu>lambdaQuery()
                .ne(SysMenu::getId, ignoreId)
                .eq(SysMenu::getPid, pid));
        if (list.isEmpty()){
            return;
        }
        //  重置排序值
        for (int i = 0; i < list.size(); i++){
            SysMenu item = list.get(i);
            int index = i + 1;
            if (item.getSort() >= sort){
                index += offset;
            }
            item.setSort(index);
        }
        //  批量保存
        this.updateBatchById(list,20);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(SysMenu params) {
        //  获取修改前的数据
        SysMenu dbMenu = this.getById(params.getId());
        Assert.notNull(dbMenu,"Id不存在");
        //  判断是否修改了父级
        if (null != params.getPid()){
            Assert.isTrue(params.getPid().equals(dbMenu.getPid()),"不允许修改父级Id");
        }
        //  判断是否修改了排序值
        if (null != params.getSort() && !params.getSort().equals(dbMenu.getSort())){
            //  重置排序值
            this.resetSort(params.getId(),params.getPid(),params.getSort(),1);
        }
        //  保存
        this.updateById(params);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Serializable id) {
        //  获取删除前的数据
        SysMenu dbMenu = this.getById(id);
        Assert.notNull(dbMenu,"Id不存在");
        //  重置排序值
        this.resetSort(dbMenu.getId(),dbMenu.getPid(),dbMenu.getSort(),-1);
        //  删除
        this.removeById(id);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<SysMenu> selectMenuList(Collection<String> authorities) {
        if (authorities.isEmpty()){
            return new ArrayList<>(0);
        }
        StringBuilder builder = new StringBuilder();
        authorities.forEach(item -> builder.append("'").append(item).append("'").append(","));
        List<SysMenu> menuList = this.baseMapper.selectByAuthorities(builder.substring(0,builder.length() - 1));
        return null == menuList ? new ArrayList<>(0) : menuList;
    }

}
