package com.baomidou.crab.sys.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.crab.common.CrabConstant;
import com.baomidou.crab.common.ErrorCode;
import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.dto.LoginDTO;
import com.baomidou.crab.sys.dto.UserDTO;
import com.baomidou.crab.sys.entity.User;
import com.baomidou.crab.sys.entity.UserRole;
import com.baomidou.crab.sys.mapper.UserMapper;
import com.baomidou.crab.sys.service.IUserRoleService;
import com.baomidou.crab.sys.service.IUserService;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.encrypt.MD5Salt;
import com.baomidou.kisso.common.util.RandomType;
import com.baomidou.kisso.common.util.RandomUtil;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-09-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public IPage<User> page(IPage<User> page, User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (RegexUtils.isEnglish(user.getRealName())) {
            user.setInitial(user.getRealName());
            user.setRealName(null);
        }
        qw.setEntity(user);
        return super.page(page, qw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveDto(UserDTO dto) {
        User user = dto.convert(User.class);
        user.setSalt(RandomUtil.getText(RandomType.MIX, 8));
        user.setPassword(MD5Salt.md5SaltEncode(user.getUsername() + user.getSalt(), dto.getPassword()));
        return super.save(user) && userRoleService.saveBatch(dto.getRoleIds().stream().map(id -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(id);
            return userRole;
        }).collect(Collectors.toList()));
    }

    @Override
    public boolean updateDtoById(UserDTO dto) {
        Assert.fail(null == dto.getId(), ErrorCode.ID_REQUIRED);
        User dbUser = super.getById(dto.getId());
        Assert.fail(null == dbUser, "修改用户不存在");
        User user = dto.convert(User.class);
        user.setPassword(MD5Salt.md5SaltEncode(dbUser.getUsername() + dbUser.getSalt(), dto.getPassword()));
        return super.updateById(user) && userRoleService.updateByIds(dto.getId(), dto.getRoleIds());
    }

    @Override
    public User loginByDto(HttpServletRequest request, HttpServletResponse response, LoginDTO dto) {
        /*
        Assert.fail(!ImageCaptcha.getInstance().verification(request,
                CaptchaController.TICKET, dto.getCode()), "验证码错误");
         */
        Assert.fail(StringUtils.isEmpty(dto.getUsername())
                || StringUtils.isEmpty(dto.getPassword()), "用户名密码不能为空");

        List<User> userList = list(Wrappers.<User>query().eq("username", dto.getUsername()));
        Assert.fail(null == userList || userList.size() > 1, "用户不存或异常数据");

        User user = userList.get(0);
        Assert.fail(!MD5Salt.md5SaltValid(user.getUsername() + user.getSalt()
                , user.getPassword(), dto.getPassword()), "登录密码错误");

        // 设置登录 COOKIE
        SSOToken st = new SSOToken();
        st.setId(user.getId());
        st.setIssuer(user.getUsername());
        st.setUserAgent(request);
        SSOHelper.setCookie(request, response, st, true);

        // 返回临时对象页面显示
        User tempUser = new User();
        tempUser.setUsername(user.getUsername());
        tempUser.setNickName(user.getNickName());
        tempUser.setAvatar(user.getAvatar());
        return tempUser;
    }

    @Override
    public boolean updateById(User user) {
        Assert.fail(null == user.getId(), ErrorCode.ID_REQUIRED);
        return super.updateById(user);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status.intValue() > -1 ? 0 : -1);
        return updateById(user);
    }

    @Override
    public User getById(Serializable id) {
        User user = baseMapper.selectById(id);
        Assert.fail(null == user, ErrorCode.ID_NOT_FOUND);
        return user;
    }

    @Override
    public boolean save(User user) {
        if (null == user) {
            return false;
        }
        return super.save(user.initialName());
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }


    @Override
    public boolean unlock(Long id, String password) {
        User user = getById(id);
        return MD5Salt.md5SaltValid(user.getUsername() + user.getSalt()
                , user.getPassword(), password);
    }

    @Override
    public boolean resetPassword(Long id) {
        User user = getById(id);
        String password = CrabConstant.DEFAULT_PASSWORD;
        User temp = new User();
        temp.setId(id);
        temp.setPassword(MD5Salt.md5SaltEncode(user.getUsername() + user.getSalt(), password));
        return super.updateById(temp);
    }
}
