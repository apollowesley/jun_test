package com.leo.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.vhr.model.Hr;
import com.leo.vhr.model.RespBean;
import com.leo.vhr.service.HRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/13
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    HRService hrService;
    @Autowired
    MyDecisionManager myDecisionManager;
    @Autowired
    MyFilter myFilter;

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        //忽略拦截
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //所有请求必须认证才能登陆
        http.authorizeRequests()
//                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>()
                {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o)
                    {
                        o.setAccessDecisionManager(myDecisionManager);
                        o.setSecurityMetadataSource(myFilter);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler()
                {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException
                    {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter pw = resp.getWriter();
                        Hr hr = (Hr) auth.getPrincipal();
                        hr.setPassword(null);
                        RespBean ok = RespBean.ok("登陆成功", hr);
                        String s = new ObjectMapper().writeValueAsString(ok);
                        pw.write(s);
                        pw.flush();
                        pw.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler()
                {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException
                    {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter pw = resp.getWriter();
                        RespBean error = RespBean.error("登陆失败！");
                        if (e instanceof LockedException)
                        {
                            error.setMsg("账户被锁定，请联系管理员！");
                        }
                        else if (e instanceof CredentialsExpiredException)
                        {
                            error.setMsg("密码过期，请联系管理员！");
                        }
                        else if (e instanceof AccountExpiredException)
                        {
                            error.setMsg("账户过期，请联系管理员！");
                        }
                        else if (e instanceof DisabledException)
                        {
                            error.setMsg("账户被禁用，请联系管理员！");
                        }
                        else if (e instanceof BadCredentialsException)
                        {
                            error.setMsg("用户名或密码输入错误！");
                        }
                        String s = new ObjectMapper().writeValueAsString(error);
                        pw.write(s);
                        pw.flush();
                        pw.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler()
                {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException
                    {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter pw = resp.getWriter();
                        pw.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功")));
                        pw.flush();
                        pw.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable()
                //没有认证时在这里处理不要重定向
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint()
        {
            @Override
            public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException
            {
                resp.setContentType("application/json;charset=utf-8");
                resp.setStatus(401);//401是没有认证
                PrintWriter pw = resp.getWriter();
                RespBean error = RespBean.error("访问失败！");
                if (e instanceof InsufficientAuthenticationException)
                {
                    error.setMsg("尚未登录，请登录！");
                }
                String s = new ObjectMapper().writeValueAsString(error);
                pw.write(s);
                pw.flush();
                pw.close();
            }
        });
    }
}
