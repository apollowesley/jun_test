package com.site.service;

import java.util.List;

import com.site.common.utils.Page;
import com.site.dto.BusiLogDto;
import com.site.dto.DownloadBusiLogDto;
import com.site.entity.BusiLog;

/**
 * @author Vincent.wang
 *
 */
public interface BusiLogService {

    /**
     * 根据ID日志
     * 
     * @param id
     *            日志ID
     * @return
     */
    public BusiLog findBusiLogById(String id);

    /**
     * 根据条件分页查询日志
     * 
     * @param page
     *            分页对象
     * @param dto
     *            日志dto对象
     * @return
     */
    public Page<BusiLog> findBusiLogByPage(Page<BusiLog> page, BusiLogDto dto);

    /**
     * 查询所有的日志
     * 
     * @return
     */
    public List<BusiLog> findBusiLogAll();

    /***
     * 根据条件查询日志，并生成byte[]
     * 
     * @param busiLogDto
     * @return
     */
    public DownloadBusiLogDto getBytes(BusiLogDto busiLogDto);

}
