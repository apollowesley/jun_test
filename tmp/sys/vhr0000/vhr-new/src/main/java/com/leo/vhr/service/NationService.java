package com.leo.vhr.service;

import com.leo.vhr.mapper.NationMapper;
import com.leo.vhr.model.Nation;
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
public class NationService
{
    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations()
    {
        return nationMapper.getAllNations();
    }
}
