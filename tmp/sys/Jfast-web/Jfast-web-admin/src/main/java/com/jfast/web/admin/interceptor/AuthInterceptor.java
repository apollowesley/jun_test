/**
 * 
 */
package com.jfast.web.admin.interceptor;

import com.jfast.common.constants.Constants;
import com.jfast.common.model.JwtToken;
import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证拦截器
 * @author zengjintao
 * @version 1.0
 * @create_at 2017年5月18日 下午2:41:05
 */
@Component
public class AuthInterceptor extends BaseInterceptor {

	private JwtToken jwtToken = JwtToken.getJwtToken(Constants.SECRET_KEY);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String target = getRequestUrl(request);
		if ("/".equals(target) || "/system/unAuth".equals(target))
			return true;
		//获取token
		String token = request.getHeader("token");
		String userId = jwtToken.parseTokenToString(token);
		if (ObjectUtils.isEmpty(token) || ObjectUtils.isEmpty(userId)) { //token不存在 或者token失效
			Map resultMap = new HashMap<>();
			resultMap.put("code", ResultCode.UN_AUTH_ERROR_CODE);
			resultMap.put("message", "用户未认证");
			renderJson(response, resultMap);
			return false;
		}
		return true;
	}
}
