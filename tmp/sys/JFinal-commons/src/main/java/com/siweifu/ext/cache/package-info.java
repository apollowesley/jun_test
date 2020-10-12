/**
 * 
 * 由于 自带 CacheInterceptor对beetl的支持问题，故扩展之
 * 
 * @title package-info.java
 * @description 
 * @company 北京思维夫网络科技有限公司
 * @author 卢春梦
 * @version 1.0
 * @created 2015年7月29日下午3:13:32
 *
 * 使用：
 * <pre>
 *     // 查询
 *     @Before(BeetlCacheInterceptor.class)
 *     @CacheName(value = "controller")
 *
 *
 *     // 清除
 *     @Before(EvictInterceptor.class)
 * </pre>
 *
 */
package com.siweifu.ext.cache;