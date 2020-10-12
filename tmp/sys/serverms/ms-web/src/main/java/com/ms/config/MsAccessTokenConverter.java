package com.ms.config;

import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2019-03-27.
 * @email 154040976@qq.com
 */
@Component
@Primary
public class MsAccessTokenConverter extends DefaultAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        final Map<String, Object> paramMap = new HashMap<>();
        OAuth2Authentication authentication
                = super.extractAuthentication(map);
        paramMap.put("age", "0");
        paramMap.put("img", "6156151451.jpg");
        authentication.setDetails(paramMap);
        return authentication;
    }

}
