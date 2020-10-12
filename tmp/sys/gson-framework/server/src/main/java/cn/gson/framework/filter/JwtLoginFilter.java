package cn.gson.framework.filter;

import cn.gson.framework.common.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.filter</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月26日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private ObjectMapper om = new ObjectMapper();

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * 用户成功登录后，生成token。
     *
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 1);
        instance.set(Calendar.HOUR_OF_DAY, 2);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        User u = (User) auth.getPrincipal();
        String token = Jwts.builder()
                .setSubject(u.getUsername())
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS512, "te1mhfawdu426jpb")
                .compact();
        res.addHeader("Authorization", "Bearer " + token);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().write(om.writeValueAsString(JsonResponse.success("登录成功")));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();
        res.setContentType("application/json;charset=utf-8");
        JsonResponse response = JsonResponse.failure(failed.getMessage());
        if (failed instanceof BadCredentialsException) {
            response.setMsg("账号或密码错误！");
        }
        res.getWriter().write(om.writeValueAsString(response));
    }
}
