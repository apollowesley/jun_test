package com.wangxin.common.framework.key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.wangxin.common.framework.key.entity.Key;
import com.wangxin.common.framework.key.properties.MasterKeyProperties;
import com.wangxin.common.framework.key.properties.SlaveKeyProperties;
import com.wangxin.common.framework.key.table.MasterTablesEnum;
import com.wangxin.common.framework.key.table.SlaveTablesEnum;
import com.wangxin.service.key.KeyService;

/** 
 * @Description 生成主键：在项目启动时，将库里的主键id全部查询出来，放到本地内存中(集合)。 <br/>
 * 主键实例：2017 0001 0001 0000 001 <br/>
 * 1-4位：机器码(应用服务器) 5-8位：表代码code 9-19位：增长id
 * @author 王鑫 
 * @date Apr 12, 2017 1:59:40 PM  
 */
@Component
public class FactoryAboutKey {

    private static final Logger log = LoggerFactory.getLogger(FactoryAboutKey.class);

    public static ConcurrentHashMap<String, String> masterTabPkValMap = new ConcurrentHashMap<String, String>();
    public static ConcurrentHashMap<String, String> slaveTabPkValMap = new ConcurrentHashMap<String, String>();

    @Autowired
    private KeyService keyService;

    @Value("${machineCode:2017}") // 若没取到机器码，则默认0001
    private String machineCode;

    /**
     * 初始化 <br/>
     * 1.从配置文件读取表名和代码的键值对,防御 <br/>
     * 2.获取机器码 <br/>
     * 3.用 （机器码、表代码、11位字符串）组装主键
     */
    @PostConstruct
    public void init() throws Exception {

        // master数据库当中获取表主名及对应主键名
        List<Key> masterTabs = keyService.getMasterTables();
        Map<String, String> masterTabMap = new HashMap<>();
        for (Key k : masterTabs) {
            masterTabMap.put(k.getTableName(), k.getId());
        }

        // 从配置读取的master db key对象
        Map<String, String> masterKeyPropertiesMap = MasterKeyProperties.getPropertiesMap();
        Map<String, Key> masterKeyFromProperiesMap = new HashMap<String, Key>();
        Key masterKey = null;
        for (Map.Entry<String, String> entry : masterKeyPropertiesMap.entrySet()) {
            masterKey = new Key();
            masterKey.setTableName(entry.getKey());
            masterKey.setMachineCode(this.machineCode);
            masterKey.setTableCode(entry.getValue());
            masterKeyFromProperiesMap.put(entry.getKey(), masterKey);
        }
        Set<String> masterTblExist = masterKeyFromProperiesMap.keySet();

        // 判断master db 表名是否在master数据库中存在
        List<Key> masterKeys = new ArrayList<>();
        for (String masterTableName : masterTblExist) {
            if (!masterTabMap.containsKey(masterTableName)) {
                throw new Exception(masterTableName + "表名在数据库中不存在或MasterTablesPKEnum类中没做相应的配置");
            }
            masterKey = masterKeyFromProperiesMap.get(masterTableName);
            masterKey.setId(masterTabMap.get(masterTableName));
            masterKeys.add(masterKey);
        }

        // 拼装master主键id
        List<Key> masterTabPkValues = keyService.getMasterTableValues(masterKeys);
        if (log.isDebugEnabled()) {
            log.debug("## masterTabPkValues.size={}", masterTabPkValues.size());
        }
        for (Key k : masterTabPkValues) {
            String newId = "";
            if (StringUtils.isBlank(k.getId())) {
                if (!masterKeyFromProperiesMap.containsKey(k.getTableName())) {
                    throw new Exception(k.getTableName() + "表名在MasterTablesPKEnum类中没做相应的配置");
                }
                newId = machineCode + masterKeyFromProperiesMap.get(k.getTableName()).getTableCode() + "00000000000";
            } else if (k.getId().length() < 19) {
                newId = machineCode + k.getId();
                for (int i = newId.length(); i < 19; i++) {
                    newId = newId + "0";
                }
            } else if (k.getId().length() > 19) {
                newId = k.getId().substring(0, 19);
            } else {
                newId = k.getId();
            }
            masterTabPkValMap.put(k.getTableName(), newId);
        }

        // ================================================================================================
        // slave数据库当中获取表主名及对应主键名
        List<Key> slaveTabs = keyService.getSlaveTables();
        Map<String, String> slaveTabMap = new HashMap<>();
        for (Key k : slaveTabs) {
            slaveTabMap.put(k.getTableName(), k.getId());
        }

        // 从配置读取的slave db key对象
        //Map<String, Key> slaveEnum = SlaveTablesEnum.getTables();
        Map<String, String> slaveKeyPropertiesMap = SlaveKeyProperties.getPropertiesMap();
        Map<String, Key> slaveKeyFromProperiesMap = new HashMap<String, Key>();
        Key slaveKey = null;
        for (Map.Entry<String, String> entry : slaveKeyPropertiesMap.entrySet()) {
            slaveKey = new Key();
            slaveKey.setTableName(entry.getKey());
            slaveKey.setMachineCode(this.machineCode);
            slaveKey.setTableCode(entry.getValue());
            slaveKeyFromProperiesMap.put(entry.getKey(), slaveKey);
        }
        Set<String> slaveTblExist = slaveKeyPropertiesMap.keySet();

        // 判断slave db 表名是否在slave数据库中存在
        List<Key> slaveKeys = new ArrayList<>();
        for (String masterTableName : slaveTblExist) {
            if (!slaveTabMap.containsKey(masterTableName)) {
                throw new Exception(masterTableName + "表名在数据库中不存在或SlaveTablesPKEnum类中没做相应的配置");
            }
            slaveKey = slaveKeyFromProperiesMap.get(masterTableName);
            slaveKey.setId(slaveTabMap.get(masterTableName));
            slaveKeys.add(slaveKey);
        }

        // 拼装slave主键id
        List<Key> slaveTabPkValues = keyService.getMasterTableValues(slaveKeys);
        if (log.isDebugEnabled()) {
            log.debug("## slaveTabPkValues.size={}", slaveTabPkValues.size());
        }
        for (Key k : slaveTabPkValues) {
            String newId = "";
            if (StringUtils.isBlank(k.getId())) {
                if (!slaveKeyFromProperiesMap.containsKey(k.getTableName())) {
                    throw new Exception(k.getTableName() + "表名在SlaveTablesPKEnum类中没做相应的配置");
                }
                newId = machineCode + slaveKeyFromProperiesMap.get(k.getTableName()).getTableCode() + "00000000000";
            } else if (k.getId().length() < 19) {
                newId = machineCode + k.getId();
                for (int i = newId.length(); i < 19; i++) {
                    newId = newId + "0";
                }
            } else if (k.getId().length() > 19) {
                newId = k.getId().substring(0, 19);
            } else {
                newId = k.getId();
            }
            slaveTabPkValMap.put(k.getTableName(), newId);
        }

    }

    private static Lock masterLock = new ReentrantLock();// 锁对象

    /**
     * 根据表名从本地内存读取所对应的id
     * @param pk 表枚举配置项
     * @return 表新产生的ID
     */
    public static String getPkByMasterDB(MasterTablesEnum pk) {
        boolean isloap = true;
        String finalId = "";
        while (isloap) {
            masterLock.lock();
            String code = masterTabPkValMap.get(pk.name());
            if (StringUtils.isEmpty(code)) {
                log.error("## 表={} ， MasterTablesPkEnum不存在相对应的键值对!", pk.name());
            } else {
                String machineCode = code.substring(0, 4);
                String keyValue = code.substring(4, 8);
                String idValue = code.substring(8, code.length());
                long id = Long.valueOf(idValue);
                id++;
                int idLenth = String.valueOf(id).length();

                String containZero = "";
                for (int i = 0; i < idValue.length() - idLenth; i++) {
                    containZero += "0";
                }
                containZero += String.valueOf(id);
                finalId = machineCode + keyValue + containZero;
                masterTabPkValMap.replace(pk.name(), finalId);
            }

            isloap = false;
            masterLock.unlock();
        }
        return finalId;
    }

    private static Lock slaveLock = new ReentrantLock();// 锁对象

    /**
     * 根据表名从本地内存读取所对应的id
     * @param pk 表枚举配置项
     * @return 表新产生的ID
     */
    public static String getPkBySlaveDB(SlaveTablesEnum pk) {
        boolean isloap = true;
        String finalId = "";
        while (isloap) {
            slaveLock.lock();
            String code = slaveTabPkValMap.get(pk.name());
            if (StringUtils.isEmpty(code)) {
                log.error("## 表={} ， SlaveTablesPKEnum不存在相对应的键值对!", pk.name());
            } else {
                String machineCode = code.substring(0, 4);
                String keyValue = code.substring(4, 8);
                String idValue = code.substring(8, code.length());
                long id = Long.valueOf(idValue);
                id++;
                int idLenth = String.valueOf(id).length();

                String containZero = "";
                for (int i = 0; i < idValue.length() - idLenth; i++) {
                    containZero += "0";
                }
                containZero += String.valueOf(id);
                finalId = machineCode + keyValue + containZero;
                slaveTabPkValMap.replace(pk.name(), finalId);
            }

            isloap = false;
            slaveLock.unlock();
        }
        return finalId;
    }
}