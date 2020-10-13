package com.baomidou.crab.v1.service.impl;

import com.baomidou.crab.v1.entity.Tag;
import com.baomidou.crab.v1.mapper.TagMapper;
import com.baomidou.crab.v1.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-02-07
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
