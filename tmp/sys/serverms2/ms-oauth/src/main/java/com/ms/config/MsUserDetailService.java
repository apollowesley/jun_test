package com.ms.config;

import com.ms.entity.MsUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:zxm
 * Date:2019/2/20
 */
@Service
public class MsUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("admin");
        simpleGrantedAuthorities.add(authority);

        String encode = bCryptPasswordEncoder.encode("1234");
        return new MsUser(s,encode , simpleGrantedAuthorities, "7876845453");
    }
}
