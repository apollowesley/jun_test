package pers.man.quickdevcore.system.service;

import pers.man.quickdevcore.system.entity.BaseDTO;
import pers.man.quickdevcore.system.entity.web.ResultBean;

import java.io.Serializable;

/**
 * 基础Service层
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-02 18:24
 * @project quick-dev
 */
public interface BaseService<T extends BaseDTO,Pk extends Serializable> {
    /**
     * 添加一条记录
     * @return
     */
    ResultBean save(T t);

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    ResultBean getById(Pk id);

    /**
     * 查询所有记录
     * @return
     */
    ResultBean getAll();

    /**
     * 修改一条记录
     * @param t
     * @return
     */
    ResultBean update(T t);

    /**
     * 删除一条记录
     * @param id
     * @return
     */
    ResultBean removeById(Pk id);

    /**
     * 删除一条记录
     * @param t
     * @return
     */
    ResultBean remove(T t);
}
