package com.leo.vhr.service;

import com.leo.vhr.mapper.HrMapper;
import com.leo.vhr.mapper.HrRoleMapper;
import com.leo.vhr.model.Hr;
import com.leo.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/13
 * @version: 1.0
 */
@Service
public class HRService implements UserDetailsService
{
    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null)
        {
            throw new UsernameNotFoundException("查无此人！");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords)
    {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    public Integer updateHr(Hr hr)
    {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids)
    {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    public Integer deleteHrById(Integer id)
    {
        return hrMapper.deleteByPrimaryKey(id);
    }
}
