package com.baomidou.crab.v1.service.impl;

import com.baomidou.crab.v1.entity.Link;
import com.baomidou.crab.v1.service.ILinkService;
import com.baomidou.crab.v1.mapper.LinkMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 友情链接表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-02-07
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

}
