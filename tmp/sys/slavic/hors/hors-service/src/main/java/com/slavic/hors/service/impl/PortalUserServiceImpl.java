package com.slavic.hors.service.impl;

import com.slavic.hors.orm.entity.HorsPortalLoginRecord;
import com.slavic.hors.orm.entity.HorsPortalUser;
import com.slavic.hors.orm.mapper.HorsPortalLoginRecordMapper;
import com.slavic.hors.orm.mapper.HorsPortalUserMapper;
import com.slavic.hors.service.PortalUserService;
import com.slavic.veles.utils.AssertUtil;
import com.slavic.veles.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PortalUserServiceImpl implements PortalUserService {

    @Resource
    private HorsPortalUserMapper horsPortalUserMapper;

    @Resource
    private HorsPortalLoginRecordMapper horsPortalLoginRecordMapper;

    @Override
    public HorsPortalUser login(String username, String password) {
        HorsPortalUser query = new HorsPortalUser();
        query.setUsername(username);
        query.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<HorsPortalUser> users = horsPortalUserMapper.queryAll(query);
        AssertUtil.assertTrue(!CollectionUtils.isEmpty(users) ,"密码错误或用户不存在");
        AssertUtil.assertTrue(1 == users.get(0).getUserStatus(),"账号已停用,如有疑问,请联系管理员");
        saveLoginRecord(users.get(0).getId());
        return users.get(0);
    }

    @Override
    public HorsPortalUser findByUserName(String username) {
        HorsPortalUser query = new HorsPortalUser();
        query.setUsername(username);
        List<HorsPortalUser> users = horsPortalUserMapper.queryAll(query);
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }

    @Override
    public HorsPortalUser findById(Long id) {
        return horsPortalUserMapper.queryById(id);
    }

    @Override
    public List<HorsPortalUser> list(HorsPortalUser query) {
        return horsPortalUserMapper.queryAll(query);
    }

    @Override
    public HorsPortalUser update(HorsPortalUser user) {
        return horsPortalUserMapper.updateById(user) == 1 ? user : null;
    }

    @Override
    public boolean delete(Long id) {
        return horsPortalUserMapper.deleteById(id) == 1;
    }

    @Override
    public boolean add(HorsPortalUser user) {
        AssertUtil.assertTrue(null == findByUserName(user.getUsername()),"用户名已存在,添加用户失败");
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        return horsPortalUserMapper.insert(user) == 1;
    }

    @Override
    public void saveLoginRecord(Long userId) {
        HorsPortalLoginRecord record = new HorsPortalLoginRecord();
        record.setUserId(userId);
        record.setLoginIp(IpUtil.getIpAddr());
        record.setIpLocation(IpUtil.getIpLocation());
        record.setCreateTime(new Date());
        horsPortalLoginRecordMapper.insert(record);
    }

    @Override
    public List<HorsPortalLoginRecord> nearLoginRecords(Long userId) {
        HorsPortalLoginRecord query = new HorsPortalLoginRecord();
        query.setUserId(userId);
        return horsPortalLoginRecordMapper.queryAll(query);
    }
}
