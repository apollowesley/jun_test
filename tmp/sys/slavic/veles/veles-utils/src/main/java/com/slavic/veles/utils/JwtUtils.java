package com.slavic.veles.utils;

import com.alibaba.fastjson.JSON;
import com.slavic.veles.base.exception.ApplyException;
import com.slavic.veles.base.response.EntryHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Slf4j
public class JwtUtils {

    public static String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "hors-portal")
                .compact();
    }

    public static String parseSubject(String token) {
        return Jwts.parser()
                .setSigningKey("hors-portal")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody()
                .getSubject();
    }

    public static <T> T parseObject(String token,Class<T> clazz) {
        String subject = Jwts.parser()
                .setSigningKey("hors-portal")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody()
                .getSubject();
        return Optional.ofNullable(JSON.parseObject(subject, clazz)).orElse(null);
    }

    public static Long getUserId() {
        try {
            String token = SpringContextHolder.getRequest().getHeader("token");
            AssertUtil.assertTrue(!StringUtils.isEmpty(token), EntryHeader.HEADER_TOKEN_INVALID);
            Long id = parseObject(token, Long.class);
            AssertUtil.assertTrue(id != null, EntryHeader.HEADER_TOKEN_INVALID);
            return id;
        } catch (Exception e) {
            throw new ApplyException(EntryHeader.HEADER_TOKEN_INVALID);
        }
    }
}
