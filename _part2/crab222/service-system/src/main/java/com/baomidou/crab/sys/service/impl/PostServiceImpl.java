package com.baomidou.crab.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.crab.common.ErrorCode;
import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.entity.Post;
import com.baomidou.crab.sys.mapper.PostMapper;
import com.baomidou.crab.sys.service.IPostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统岗位表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-10-21
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Override
    public IPage<Post> page(Page page, Post post) {
        QueryWrapper<Post> qw = new QueryWrapper<>();
        if (RegexUtils.isEnglish(post.getName())) {
            post.setInitial(post.getName());
            post.setName(null);
        }
        qw.setEntity(post);
        return super.page(page, qw);
    }

    @Override
    public boolean save(Post post) {
        if (null == post) {
            return false;
        }
        return super.save(post.initialName());
    }

    @Override
    public boolean updateById(Post post) {
        Assert.fail(null == post.getId(), ErrorCode.ID_REQUIRED);
        return super.updateById(post);
    }


    @Override
    public boolean updateStatus(Long id, Integer status) {
        Post post = new Post();
        post.setId(id);
        post.setStatus(status);
        return updateById(post);
    }
}
