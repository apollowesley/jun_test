package org.neuedu.his.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.neuedu.his.entity.ConstantCategory;
import org.neuedu.his.mapper.ConstantCategoryMapper;
import org.neuedu.his.service.IConstantCategoryService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 常数类别 常数类别 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Slf4j
@Service
public class ConstantCategoryServiceImpl extends ServiceImpl<ConstantCategoryMapper, ConstantCategory> implements IConstantCategoryService {


    @Override
    public Integer getIdByConstantCategoryName(String constantCategoryName) {
        ConstantCategory category = new LambdaQueryChainWrapper<>(baseMapper)
                .select(ConstantCategory::getId)
                .eq(ConstantCategory::getConstantName, constantCategoryName).one();
        if(Objects.nonNull(category)){
            return category.getId();
        }
        return -1;
    }
}
