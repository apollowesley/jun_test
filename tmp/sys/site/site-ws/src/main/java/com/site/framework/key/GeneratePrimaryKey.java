package com.site.framework.key;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.site.framework.key.model.Key;
import com.site.framework.key.service.KeyService;

/**
 * 
 * 生成主键：在项目启动时，将库里的主键id全部查询出来，放到本地内存中(集合)。 <br/>
 * 主键实例：2014 0001 0001 0000 001 <br/>
 * 1-4位：机器码(应用服务器) 5-8位：表代码code 9-19位：增长id
 *
 */
@Component
public class GeneratePrimaryKey {

    private static final Logger log = LoggerFactory.getLogger(GeneratePrimaryKey.class);

    public static ConcurrentHashMap<String, String> primaryKeyMap = new ConcurrentHashMap<String, String>();

    private static Lock lock = new ReentrantLock();// 锁对象

    @Autowired
    private KeyService keyService;

    /** 机器码 */
    @Value("#{configProperties['machineCode']}")
    private String machineCode;

    /**
     * 初始化 <br/>
     * 1.从配置文件读取表名和代码的键值对,防御 <br/>
     * 2.获取机器码 <br/>
     * 3.用 （机器码、表代码、11位字符串）组装主键
     */
    @PostConstruct
    public void init() throws Exception {

        Key key = null;

        // 判断表名是否在数据库中存在
        Map<String, Key> tableMap = new HashMap<String, Key>();
        List<Key> tables = keyService.getTables();

        if (log.isDebugEnabled()) {
            log.debug("## 机器码={}", machineCode);
            log.debug("## tables={}", JSON.toJSONString(tables));
        }

        for (Key k : tables) {
            tableMap.put(k.getTableName(), k);
        }

        Map<String, String> propertyMap = KeyProperty.getPropertyMap(); // 获取表名所对应的代码
        String tableName = null;
        for (Entry<String, String> entry : propertyMap.entrySet()) {
            tableName = entry.getKey();
            if (!tableMap.containsKey(tableName)) {
                throw new Exception("## 表名在数据库中不存在或key.properties配置文件中没有做相应的配置:" + tableName);
            }
            key = tableMap.get(tableName);
            key.setMachineCode(machineCode);
            tableMap.put(tableName, key);
        }

        List<Key> pkValues = keyService.getTableValues(tables);
        String newId = null;
        int length = 0;
        for (Key k : pkValues) {
            if (StringUtils.isEmpty(k.getId())) {
                primaryKeyMap.put(k.getTableName(), machineCode + propertyMap.get(k.getTableName()) + "00000000000");
            } else if (StringUtils.isNotEmpty(k.getId()) && k.getId().length() < 19) {
                newId = machineCode + k.getId();
                if (newId.length() > 19) {
                    newId = newId.substring(0, 19);
                } else {
                    length = newId.length();
                    for (int i = length; i < 19; i++) {
                        newId = newId + "0";
                    }
                }
                primaryKeyMap.put(k.getTableName(), newId);
            } else {
                primaryKeyMap.put(k.getTableName(), k.getId());
            }
        }
    }

    /**
     * 根据表名从本地内存读取所对应的id
     */
    public static String getPkValue(String tableName) {
        boolean isloap = true;
        String finalId = "", code = "", machineCode = "", keyValue = "", idValue = "", containZero = "";
        int id = 0, idLenth = 0;
        while (isloap) {
            lock.lock();
            code = primaryKeyMap.get(tableName.toUpperCase());
            if (StringUtils.isEmpty(code)) {
                log.error("## 表={} ， 配置文件中(key.properties)不存在相对应的键值对!", tableName);
            } else {
                machineCode = code.substring(0, 4);
                keyValue = code.substring(4, 8);
                idValue = code.substring(8, code.length());
                id = Integer.valueOf(idValue);
                id++;
                idLenth = String.valueOf(id).length();

                containZero = "";
                for (int i = 0; i < idValue.length() - idLenth; i++) {
                    containZero += "0";
                }
                containZero += String.valueOf(id);
                finalId = machineCode + keyValue + containZero;
                primaryKeyMap.replace(tableName, finalId);
            }

            isloap = false;
            lock.unlock();
        }
        return finalId;
    }

}