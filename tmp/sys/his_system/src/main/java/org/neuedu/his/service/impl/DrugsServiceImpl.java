package org.neuedu.his.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.neuedu.his.entity.Drugs;
import org.neuedu.his.entity.dto.DrugsDTO;
import org.neuedu.his.mapper.DrugsMapper;
import org.neuedu.his.service.IDrugsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 非药品收费项目 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Service
public class DrugsServiceImpl extends ServiceImpl<DrugsMapper, Drugs> implements IDrugsService {

    @Override
    public List<DrugsDTO> excelExport(String search) {
        //查询的条件构造器
        QueryWrapper<DrugsDTO> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(search),"drugs_name",search)
                .or()
                .like(StringUtils.isNotBlank(search),"mnemonic_code",search);

        return baseMapper.excelExport(wrapper);
    }
}
