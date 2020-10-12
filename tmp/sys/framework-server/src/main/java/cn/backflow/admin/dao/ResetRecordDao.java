package cn.backflow.admin.dao;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.ResetRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ResetRecordDao extends BaseMyBatisDao<ResetRecord, Integer> {

    public Class<ResetRecord> getEntityClass() {
        return ResetRecord.class;
    }

    public int saveOrUpdate(ResetRecord resetRecord) {
        return resetRecord.getId() == null ? save(resetRecord) : update(resetRecord);
    }

    public Page<ResetRecord> findByPageRequest(PageRequest pageRequest) {
        return pageQuery("ResetRecord.paging", pageRequest);
    }

    public ResetRecord getNewestByEmail(String email) {
        return getSqlSession().selectOne("ResetRecord.getNewestByEmail", email);
    }

    /**
     * 更新重置申请的有效状态
     *
     * @param filter 过滤条件，值可为 username, email, resetKey
     * @param value  匹配值
     * @param valid  有效状态
     * @return effected rows
     */
    public int updateValidState(String filter, String value, int valid) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(filter, value);
        params.put("valid", valid);
        return getSqlSession().update("ResetRecord.updateValidState", params);
    }
}