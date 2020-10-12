package com.antdsp.common.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import com.antdsp.utils.Constants;

public class AntdspShiroSessionManager extends DefaultWebSessionManager{
	
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    
    public AntdspShiroSessionManager() {
        super();
    }

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		 HttpServletRequest httpRequest = WebUtils.toHttp(request);
		
		String sessionId = httpRequest.getHeader(Constants.AUTHORIZATION);
		if(!StringUtils.isEmpty(sessionId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID , true);
            
            return sessionId;
		}else {
			return super.getSessionId(request, response);
		}
	}

}
