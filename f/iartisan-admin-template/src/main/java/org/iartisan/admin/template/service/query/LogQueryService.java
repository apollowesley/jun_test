package org.iartisan.admin.template.service.query;

import org.iartisan.admin.template.dao.mapper.SystemLogMapper;
import org.iartisan.admin.template.service.entity.EChartEntity;
import org.iartisan.admin.template.service.entity.LogEntity;
import org.iartisan.runtime.support.BaseQueryServiceSupport;
import org.iartisan.runtime.utils.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日志 query
 *
 * @author King
 * @since 2018/4/17
 */
@Service
public class LogQueryService extends BaseQueryServiceSupport<SystemLogMapper, LogEntity> {


    public List<EChartEntity> getLogCharts() {
        List<Map<String, String>> dbResult = baseMapper.selectGroupByMethod();
        if (CollectionUtil.isNotEmpty(dbResult)) {
            List<EChartEntity> result = new ArrayList<>();
            for (Map<String, String> stringMap : dbResult) {
                EChartEntity entity = new EChartEntity();
                entity.setName(stringMap.get("name"));
                entity.setValue(stringMap.get("value"));
                result.add(entity);
            }
            return result;
        }
        return null;
    }
}
