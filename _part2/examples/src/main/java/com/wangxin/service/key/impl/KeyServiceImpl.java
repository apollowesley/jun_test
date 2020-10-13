package com.wangxin.service.key.impl;

import java.util.ArrayList;
import java.util.List;

//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangxin.common.framework.datasource.DataSourceEnum;
import com.wangxin.common.framework.datasource.DataSourceRouter;
import com.wangxin.common.framework.key.entity.Key;
import com.wangxin.mapper.key.KeyMapper;
import com.wangxin.service.key.KeyService;

/** 
 * @Description 主键生成
 * @author 王鑫 
 * @date Apr 12, 2017 1:48:30 PM  
 */
@Service("keyService")
public class KeyServiceImpl implements KeyService {

    @Autowired
    private KeyMapper keyMapper;

    public void setKeyMapper(KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    @DataSourceRouter(DataSourceEnum.master)
    @Override
    public List<Key> getMasterTableValues(List<Key> keys) {
        List<Key> keyList = new ArrayList<Key>();
        try {
            keyList = keyMapper.getTableValues(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyList;
    }

    @DataSourceRouter(DataSourceEnum.slave)
    @Override
    public List<Key> getSlaveTableValues(List<Key> keys) {
        List<Key> keyList = new ArrayList<Key>();
        try {
            keyList = keyMapper.getTableValues(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyList;
    }

    @DataSourceRouter(DataSourceEnum.master)
    @Override
    public List<Key> getMasterTables() {
        List<Key> keyList = new ArrayList<Key>();
        try {
            keyList = keyMapper.getTablesByMySQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyList;
    }

    @DataSourceRouter(DataSourceEnum.slave)
    @Override
    public List<Key> getSlaveTables() {
        List<Key> keyList = new ArrayList<Key>();
        try {
            keyList = keyMapper.getTablesByMySQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyList;
    }
}
