package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface CompanyMapper extends BaseMapper<Company> {
    int insertOrUpdate(Company record);

    int insertOrUpdateSelective(Company record);
}