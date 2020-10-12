package cn.gson.framework.handler;

import cn.gson.framework.common.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.handler</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月25日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Component
public class AuthenticationHandler implements LogoutSuccessHandler {
    private ObjectMapper om = new ObjectMapper();

    /**
     * 退出登录成功
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.output(response, JsonResponse.success());
    }

    private void output(HttpServletResponse response, JsonResponse jsonResponse) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().append(om.writeValueAsString(jsonResponse));
    }
}
