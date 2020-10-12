package com.leo.vhr.service;

import com.leo.vhr.mapper.PoliticsstatusMapper;
import com.leo.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/24
 * @version: 1.0
 */
@Service
public class PoliticsstatusService
{
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Politicsstatus> getAllPoliticsstatus()
    {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
