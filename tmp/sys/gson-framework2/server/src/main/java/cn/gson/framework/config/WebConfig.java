package cn.gson.framework.config;

import cn.gson.framework.converter.DateConverter;
import cn.gson.framework.filter.JwtAuthenticationFilter;
import cn.gson.framework.filter.JwtLoginFilter;
import cn.gson.framework.handler.AuthenticationHandler;
import cn.gson.framework.model.enums.Status;
import cn.gson.framework.model.pojo.Account;
import cn.gson.framework.resolver.JwtUserHandlerMethodArgumentResolver;
import cn.gson.framework.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.config</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月25日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.IGNORED_ORDER)
public class WebConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    JwtUserHandlerMethodArgumentResolver resolver;

    @Autowired
    AuthenticationHandler authenticationHandler;

    @Autowired
    AccountService accountService;

    @Bean
    public UserDetailsService getUserDetailsService() {
        return username -> {
            QueryWrapper<Account> qw = new QueryWrapper<>();
            qw.lambda().eq(Account::getAccount, username);
            Account userDto = accountService.getOne(qw);
            if (userDto == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return User.withUsername(userDto.getId() + ":" + userDto.getAccount())
                    .password(userDto.getPassword())
                    .authorities(new String[]{})
                    .accountLocked(userDto.getStatus().equals(Status.锁定))
                    .disabled(userDto.getStatus().equals(Status.禁用)).build();
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .anyRequest().authenticated()
                .and().logout()
                .logoutSuccessHandler(authenticationHandler)
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), getUserDetailsService()));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getUserDetailsService()).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }

    /**
     * 请求日期参数转换
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }
}
