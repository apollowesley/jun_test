package pers.man.quickdevcore.system.persistence;

import pers.man.quickdevcore.system.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 基础Mapper,有基础的增删改查方法
 * 在编写具体实体类的Mapper层接口时只需集成该接口即可,不用重写该接口里的任何方法
 * 在编写Mapper.xml时参考BaseMapper的写法
 * @author MAN
 * @version 1.0
 * @date 2020-04-02 18:15
 * @project quick-dev
 */
public interface BaseMapper<T extends BaseEntity, PK extends Serializable> {
    /**
     * 添加一个实体
     */
    void save(final T t);

    /**
     * 批量添加实体
     */
    void bathSave(final List<T> list);

    /**
     * 根据id查询一个实体
     *
     * @param id
     * @return
     */
    T getById(final PK id);

    /**
     * 查询所有实体
     *
     * @return
     */
    List<T> listAll();

    /**
     * 统计实体数量
     *
     * @return
     */
    Long count();

    /**
     * 更新一个实体
     */
    void update(final T t);

    /**
     * 根据id删除一个实体
     */
    void removeById(final PK id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void bathRemoveByIds(final PK[] ids);
}
