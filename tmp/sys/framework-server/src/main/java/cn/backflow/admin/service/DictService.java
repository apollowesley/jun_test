package cn.backflow.admin.service;

import cn.backflow.admin.common.Constants;
import cn.backflow.admin.dao.DictDao;
import cn.backflow.admin.entity.Dict;
import cn.backflow.admin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Lazy(false)
@CacheConfig(cacheNames = Constants.SYS_CACHE)
public class DictService extends BaseService<Dict, Integer> {

    private final DictDao dictDao;

    @Autowired
    public DictService(DictDao dictDao) {
        this.dictDao = dictDao;
    }

    @Cacheable(key = "'dict_' + #code")
    public Map<Comparable, Dict> findMapByCode(String code) {
        return dictDao.findMapByCode(code, "code");
    }

    public List<Dict> findByCode(String code) {
        return dictDao.findByCode(code);
    }

    @Override
    @Transactional
    @CacheEvict(key = "'dict_' + #dict.code")
    public int save(Dict dict) throws DataAccessException {
        return super.save(dict);
    }

    @Override
    @Transactional
    @CacheEvict(key = "'dict_' + #dict.code")
    public int update(Dict dict) throws DataAccessException {
        dictDao.deleteByCode(dict.getCode());
        return super.update(dict);
    }

    @Override
    @Transactional
    @CacheEvict(key = "'dict_' + #dict.code")
    public int saveOrUpdate(Dict dict) {
        return dict.getId() == null ? save(dict) : update(dict);
    }

    @Transactional
    @CacheEvict(key = "'dict_' + #code")
    public int saveOrUpdate(Collection<Dict> dicts, String code) {
        dictDao.deleteByCode(code);
        return dictDao.insertBatch(dicts);
    }

    @Transactional
    @CacheEvict(key = "'dict_' + #dicts[0].code")
    public int saveOrUpdate(Collection<Dict> dicts) {
        if (dicts.isEmpty()) {
            return 0;
        }
        return saveOrUpdate(dicts, dicts.iterator().next().getCode());
    }

    @Transactional
    @CacheEvict(key = "'dict_' + #code")
    public int deleteByCode(String code) {
        return dictDao.deleteByCode(code);
    }


}