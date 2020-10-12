package com.ms.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author:zxm
 * Date:2019/2/20
 */
@Configuration
@EnableWebSecurity
public class MsSecurityConfig extends WebSecurityConfigurerAdapter {

    private boolean isAjax(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(ajaxFlag);
    }

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
                SavedRequest savedRequest = requestCache.getRequest(request, response);
                HttpSession session = request.getSession(false);
                if (session != null) {
                    Object attribute = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                    System.out.println(attribute);
                }
                if (isAjax(request)) {
                    if (savedRequest == null) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        return;
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("redirect_uri", savedRequest.getRedirectUrl());
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    try (PrintWriter out = response.getWriter()) {
                        out.append(jsonObject.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    if (savedRequest == null) {
                        super.onAuthenticationSuccess(request, response, authentication);
                        return;
                    }
                    clearAuthenticationAttributes(request);

                    getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
                }

            }
        };
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                if (isAjax(httpServletRequest)) {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                } else {
                    //微服务下一般无需 http请求 需要的话 可以配置默认统一登录地址 授权码形式获取token
                    httpServletResponse.sendRedirect("http://192.168.0.114:8899/auth/login");
                }

            }
        }).and().authorizeRequests().antMatchers("/auth/**", "/oauth/**", "/login/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin().loginPage("/login").successHandler(successHandler).failureHandler(
                new SimpleUrlAuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
                    }
                }
        ).permitAll().and().logout();

        http.httpBasic().disable();
        http.csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**", "/js/**");
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(msUserDetailService());
        return authenticationProvider;
    }

    @Bean
    public MsUserDetailService msUserDetailService() {
        return new MsUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
