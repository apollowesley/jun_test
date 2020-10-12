package com.leo.vhr.service;

import com.leo.vhr.mapper.PositionMapper;
import com.leo.vhr.model.Position;
import com.leo.vhr.model.RespBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/16
 * @version: 1.0
 */
@Service
public class PositionService
{
    @Autowired
    PositionMapper positionMapper;

    public List<Position> getAllPositions()
    {
        return positionMapper.getAllPositions();
    }

    public Integer addPositions(Position position)
    {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insertSelective(position);
    }

    public Integer updatePositions(Position position)
    {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public Integer deletePositionsById(Integer id)
    {
        return positionMapper.deleteByPrimaryKey(id);
    }

    public Integer deletePositionsByIds(@Param("ids") Integer[] ids)
    {
        return positionMapper.deletePositionByAll(ids);
    }
}
