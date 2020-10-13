package org.iartisan.admin.template.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.iartisan.admin.template.dao.mapper.SystemMessageMapper;
import org.iartisan.admin.template.dao.model.SystemMessageDO;
import org.iartisan.admin.template.service.entity.MessageEntity;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.support.BaseQueryServiceSupport;
import org.springframework.stereotype.Service;

/**
 * 消息查询服务
 *
 * @author King  2019-9-29
 */
@Service
public class SystemMsgQueryService extends BaseQueryServiceSupport<SystemMessageMapper, MessageEntity> {
    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadCount(String userNo) {
        SystemMessageDO dbQuery = new SystemMessageDO();
        dbQuery.setReceiverId(userNo);
        QueryWrapper<SystemMessageDO> queryWrapper = new QueryWrapper<>(dbQuery);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        return count.intValue();
    }

    public PageWrapper<MessageEntity> getPageData(Pagination page, String custNo) {
        SystemMessageDO dbQuery = new SystemMessageDO();
        dbQuery.setReceiverId(custNo);
        QueryWrapper wrapper = new QueryWrapper(dbQuery);
        return super.getPageDataByWrapper(page, wrapper);
    }
}
