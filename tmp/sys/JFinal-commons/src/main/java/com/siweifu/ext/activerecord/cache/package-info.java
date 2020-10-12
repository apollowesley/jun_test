/**
 * JFinal activerecord 的 redis cache插件
 * @author L.cm
 */
package com.siweifu.ext.activerecord.cache;

/**
 * 配置方法1：
 * <pre>
 * // redis插件
 * RedisPlugin redisPlugin = new RedisPlugin("main", "127.0.0.1");
 * // 需要手动启动reids插件
 * redisPlugin.start
 * 
 * me.add(redisPlugin);
 * 
 * // 默认使用redis main cache
 * RedisCache redisCache = new RedisCache();
 * // 设置缓存5分钟，该参数默认为0，永不超时
 * redisCache.setDefaultExpiration(5 * 60);
 * 
 * ActiveRecordPlugin arpMysql = new ActiveRecordPlugin(dataSource);
 * arpMysql.setCache(cache);
 * 
 * me.add(arpMysql);
 * </pre>
 * 
 * 配置方法2：
 * <pre>
 * // 为了避免手动启动redis插件，故采用了在jfinal启动完成时配置的
 * public void afterJFinalStart() {
 *     RedisCache redisCache = new RedisCache();
 *     // 设置缓存5分钟，该参数默认为0，永不超时
 *     redisCache.setDefaultExpiration(5 * 60);
 *     // 反射cache到db配置
 *     redisCache.intoDb();
 * }
 * </pre>
 * 
 * 
 * 使用方法：
 * <pre>
 * // 缓存 cacheName格式 cacheName#timeOut
 * // 不包含`#`时使用  默认的超时时间，默认超时时间需要配置
 * Db.findByCache("blog#300", "findall", "select * from blog");
 * </pre>
 */
