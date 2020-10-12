<#include "java_copyright.include">
package ${basepackage}.service;

import ${basepackage}.dao.BaseRepositoryTools;
import com.amez.util.PageVo;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

<#include "java_author.include">
@NoRepositoryBean
public class BaseServiceTools<T, D extends BaseRepositoryTools<T, I>, I extends Number> {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * dao层
     */
    @Autowired
    public D dao;


    /**
     * 查询单条数据调用
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public T findOne(I id) {
        return dao.findOne(id);
    }

    /**
     * 统计全部数据调用
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long count() {
        return dao.count();
    }

    /**
     * 删除一个实体集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteInBatch(Iterable<T> delList) {
        dao.deleteInBatch(delList);
    }

    /**
     * 删除一条集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(I id) {
        dao.delete(id);
    }

    /**
     * 保存多条集合
     */
    public List<T> save(List<T> dataList) {
        return dao.save(dataList);
    }

    /**
     * 保存一条集合
     */
    public T save(T dataList) {
        return dao.save(dataList);
    }

    /**
     * 强制执行持久化添加
     *
     * @param detail
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public T saveAndFlush(T detail) {
        return dao.saveAndFlush(detail);
    }

    /**
     * 执行缓存与数据库同步
     */
    public void flush() {
        dao.flush();
    }

    /**
     * 分页查询（含排序功能：实现1）
     *
     * @param example
     * @param pageNo
     * @param pageSize
     * @param sortType
     * @param direction
     * @return
     */
    public PageVo<T> findByPage(Specification<T> example, int pageNo, int pageSize, String sortType, String direction) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, sortType, direction);
        Page<T> pageResult = dao.findAll(example, pageRequest);
        return (PageVo<T>) PageVo.getPageVo(pageNo, pageSize, pageResult);
    }

    /**
     * 分页查询（含排序功能：实现2）
     *
     * @param example
     * @param pageNo
     * @param pageSize
     * @param sortType
     * @param direction
     * @return
     */
    public PageVo<T> findByPage(Example<T> example, int pageNo, int pageSize, String sortType, String direction) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, sortType, direction);
        Page<T> pageResult = dao.findAll(example, pageRequest);
        return (PageVo<T>) PageVo.getPageVo(pageNo, pageSize, pageResult);
    }

    /**
     * 配置分页请求
     *
     * @param pageNo     当前页
     * @param pageSize   每页条数
     * @param sortType 排序字段
     * @param direction  排序方向
     */
    public PageRequest buildPageRequest(int pageNo, int pageSize, String sortType, String direction) {
        Sort sort = null;
        if (!StringUtils.isNotBlank(sortType)) {
            return new PageRequest(pageNo - 1, pageSize);
        } else if (StringUtils.isNotBlank(direction)) {
            if (Sort.Direction.ASC.name().equals(direction.toUpperCase())) {
                sort = new Sort(Sort.Direction.ASC, sortType);
            } else {
                sort = new Sort(Sort.Direction.DESC, sortType);
            }
            return new PageRequest(pageNo - 1, pageSize, sort);
        } else {
            sort = new Sort(Sort.Direction.ASC, sortType);
            return new PageRequest(pageNo - 1, pageSize, sort);
        }
    }
}