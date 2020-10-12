package com.zlkj.shiro.dyprem.dao;

import java.util.List;

import com.zlkj.shiro.dyprem.entity.UrlFilter;
public interface UrlFilterDao {

    public UrlFilter createUrlFilter(UrlFilter urlFilter);
    public UrlFilter updateUrlFilter(UrlFilter urlFilter);
    public void deleteUrlFilter(Long urlFilterId);
    public UrlFilter findOne(Long urlFilterId);
    public List<UrlFilter> findAll();
}
