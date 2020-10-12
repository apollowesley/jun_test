package com.leo.vhr.service;

import com.leo.vhr.mapper.JobLevelMapper;
import com.leo.vhr.model.JobLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/17
 * @version: 1.0
 */
@Service
public class JobLevelService
{
    @Autowired
    JobLevelMapper jobLevelMapper;
    public List<JobLevel> getAllJobLevels()
    {
        return jobLevelMapper.getAllJobLevels();
    }

    public Integer addJobLevels(JobLevel jobLevel)
    {
        jobLevel.setEnabled(true);
        jobLevel.setCreateDate(new Date());
        return jobLevelMapper.insertSelective(jobLevel);
    }

    public Integer updateJobLevels(JobLevel jobLevel)
    {
        return jobLevelMapper.updateByPrimaryKeySelective(jobLevel);
    }

    public Integer delJobLevelById(Integer id)
    {
        return jobLevelMapper.deleteByPrimaryKey(id);
    }

    public Integer delJobLevelByIds(Integer[] ids)
    {
        return jobLevelMapper.deleteJobLevelsByIds(ids);
    }
}
