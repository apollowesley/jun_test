package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface PositionMapper extends BaseMapper<Position> {
    int insertOrUpdate(Position record);

    int insertOrUpdateSelective(Position record);
}