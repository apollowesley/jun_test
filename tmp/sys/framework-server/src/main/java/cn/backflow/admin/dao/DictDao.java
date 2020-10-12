package cn.backflow.admin.dao;

import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.Dict;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class DictDao extends BaseMyBatisDao<Dict, Integer> {

    /**
     * 查询返回
     *
     * @param code   字典编码
     * @param mapKey map对象的key属性
     * @return Map&lt;mapKey, Dict&gt; 对象
     */
    public Map<Comparable, Dict> findMapByCode(String code, String mapKey) {
        return getSqlSession().selectMap("Dict.findByCode", code, mapKey);
    }

    public List<Dict> findByCode(String code) {
        return getSqlSession().selectList("Dict.findByCode", code);
    }

    public int insertBatch(Collection<Dict> dicts) {
        return getSqlSession().insert("Dict.insertBatch", dicts);
    }

    public int deleteByCode(String code) {
        return getSqlSession().delete("Dict.deleteByCode", code);
    }
}