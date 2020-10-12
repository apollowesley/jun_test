package org.neuedu.his.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.neuedu.his.entity.ConstantCategory;

/**
 * <p>
 * 常数类别 常数类别 服务类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
public interface IConstantCategoryService extends IService<ConstantCategory> {

    /**
     * @apiNote 通过常数类别名称获取对应的ID
     * @param constantCategoryName 常数类别名称
     * @return
     */
    Integer getIdByConstantCategoryName(String constantCategoryName);

}
