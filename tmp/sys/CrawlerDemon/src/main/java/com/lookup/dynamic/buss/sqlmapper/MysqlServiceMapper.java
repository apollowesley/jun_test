/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.buss.sqlmapper;

import com.lookup.dynamic.model.ProxyInfo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("mysqlServiceMapper")
public interface MysqlServiceMapper {

    /**
     * 获取代理地址列表
     *
     * @param envType 环境类型  调试| 生产
     * @return
     */
    public List<String> getProxyList(String envType);

    public int insertProxy(ProxyInfo proxyInfo);
}
