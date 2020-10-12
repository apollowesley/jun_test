package com.site.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.site.common.utils.Page;
import com.site.common.utils.date.DateUtil;
import com.site.dto.BusiLogDto;
import com.site.dto.DownloadBusiLogDto;
import com.site.entity.BusiLog;
import com.site.repository.MongoRepositoryImpl;
import com.site.service.BusiLogService;

/**
 * @author Vincent.wang
 *
 */
@Service
public class BusiLogServiceImpl extends MongoRepositoryImpl<BusiLog> implements BusiLogService {

    private static final Logger log = LoggerFactory.getLogger(BusiLogServiceImpl.class);

    @Override
    public Class<BusiLog> getEntityClass() {
        return BusiLog.class;
    }

    @Override
    public BusiLog findBusiLogById(String id) {
        log.warn("## find BusiLog By Id , id={}", id);
        return findOne(id);
    }

    @Override
    public List<BusiLog> findBusiLogAll() {
        return findAll();
    }

    @Override
    public Page<BusiLog> findBusiLogByPage(Page<BusiLog> page, BusiLogDto dto) {
        return findPagination(page, getQuery(dto));
    }

    @Override
    public DownloadBusiLogDto getBytes(BusiLogDto busiLogDto) {
        List<BusiLog> bls = find(getQuery(busiLogDto));
        StringBuilder csv = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(bls)) {
            for (BusiLog bl : bls) {
                csv.append(bl.getId() + "\t,\t");
                csv.append(bl.getSystemName() + "\t,\t");
                csv.append(DateUtil.dateToString(bl.getCreateTime(), DateUtil.fm_yyyy_MM_dd_HHmmssSSS) + "\t,\t");
                csv.append(bl.getLevel() + "\t,\t");
                csv.append(bl.getThreadName() + "\t,\t");
                csv.append(bl.getLogName() + "\t,\t");
                csv.append(bl.getMessage() + "\t\r\n");
            }
        }
        DownloadBusiLogDto dto = new DownloadBusiLogDto();
        dto.setBytes(csv.toString().getBytes());
        dto.setFileName("共导出" + bls.size() + "条.csv");
        return dto;
    }

    private Query getQuery(BusiLogDto dto) {
        Query query = new Query();
        if (dto != null) {
            if (null != dto.getBeginTime() && null != dto.getEndTime())
                query.addCriteria(Criteria.where("create_time").gte(dto.getBeginTime()).lte(dto.getEndTime()));

            if (StringUtils.isNotBlank(dto.getSystemName()))
                query.addCriteria(Criteria.where("system_name").is(dto.getSystemName()));

            if (StringUtils.isNotBlank(dto.getLevel()))
                query.addCriteria(Criteria.where("level").is(dto.getLevel()));

            if (StringUtils.isNotBlank(dto.getThreadName()))
                query.addCriteria(Criteria.where("thread_name").regex(".*?" + dto.getThreadName() + ".*"));

            if (StringUtils.isNotBlank(dto.getLogName()))
                query.addCriteria(Criteria.where("log_name").regex(".*?" + dto.getLogName() + ".*"));

            if (StringUtils.isNotBlank(dto.getMessage()))
                query.addCriteria(Criteria.where("message").regex(".*?" + dto.getMessage() + ".*"));
        }
        return query;
    }

}
