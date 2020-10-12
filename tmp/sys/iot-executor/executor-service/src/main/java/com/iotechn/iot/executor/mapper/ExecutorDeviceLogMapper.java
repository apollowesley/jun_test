package com.iotechn.iot.executor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.iot.executor.entity.ExecutorDeviceLogDo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-01-12
 * Time: 下午4:18
 */
public interface ExecutorDeviceLogMapper extends BaseMapper<ExecutorDeviceLogDo> {

    public List<String> selectKeys(String secretKey);

}
