package cn.gson.framework.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.filter</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月25日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        setAuthentication(req);

        chain.doFilter(req, res);
    }

    private void setAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("te1mhfawdu426jpb")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            String user = claims.getSubject();
            SecurityContext context = SecurityContextHolder.getContext();
            if (user != null && context.getAuthentication() == null) {
                String username = user.split(":")[1];
                UserDetails details = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, null, details.getAuthorities());
                authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authRequest);
            }
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("该账号已过期,请重新登陆");
        }
    }
}
