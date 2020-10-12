package com.handy.service.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.handy.service.entity.sys.SysResource;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:28
 */
public interface ISysResourceService extends IService<SysResource>{
    /**
     * 根据ids查询
     *
     * @param idList
     * @return
     */
    List<SysResource> findByIdList(List<Long> idList);
}
