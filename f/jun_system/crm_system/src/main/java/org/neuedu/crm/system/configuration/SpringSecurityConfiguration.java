package org.neuedu.crm.system.configuration;

import lombok.extern.slf4j.Slf4j;
import org.neuedu.crm.system.entity.SysUser;
import org.neuedu.crm.system.response.ResponseCode;
import org.neuedu.crm.system.response.ResponseEntity;
import org.neuedu.crm.system.service.ISysUserService;
import org.neuedu.crm.system.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/20-16:29
 **/
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ISysUserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/index.html", "/static/**", "/favicon.ico")
            .antMatchers("/swagger-ui.html","/webjars/**","/swagger-resources/**","/v2/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/aa").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").usernameParameter("logName").passwordParameter("logPwd")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    //把登录密码设置为null，再返回到前端
                    SysUser sysUser = ((SysUser) authentication.getPrincipal()).setLoginPassword(null);
                    this.writeData(httpServletResponse, ResponseEntity.ok("登录成功~",sysUser));
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    ResponseEntity responseEntity;
                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                        responseEntity = ResponseEntity.exception(ResponseCode.USERNAME_PASSWORD_ERROR);
                    } else if (e instanceof DisabledException) {
                        responseEntity = ResponseEntity.exception(ResponseCode.ACCOUNT_DISABLED_EXCEPTION);
                    } else {
                        responseEntity = ResponseEntity.exception(ResponseCode.SYSTEM_EXCEPTION);
                    }
                    this.writeData(httpServletResponse, responseEntity);
                })
                .and()
                .logout().logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    this.writeData(httpServletResponse, ResponseEntity.ok("注销成功~"));
                })
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    this.writeData(response, ResponseEntity.exception(ResponseCode.NOT_LOGIN));
                })
                .accessDeniedHandler((request, response, authException) -> {
                    this.writeData(response, ResponseEntity.exception(ResponseCode.NOT_AUTHORIZED));
                })
                .and()
                .csrf().disable();
    }

    /**
     * 数据响应
     * @param response
     * @param responseEntity
     */
    private void writeData(HttpServletResponse response,ResponseEntity responseEntity) {
        try {
            //设置返回头
            response.setContentType("application/json;charset=utf-8");
            //设置返回状态码
            response.setStatus(response.getStatus());
            //获取输出流
            PrintWriter out = response.getWriter();
            //返回前端的数据
            out.write(JsonUtil.ToStringIgnoreNull(responseEntity));
            //关闭流
            out.close();
            //刷新缓冲区
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException("JSON序列化错误,数据写入错误~");
        }
    }

}
