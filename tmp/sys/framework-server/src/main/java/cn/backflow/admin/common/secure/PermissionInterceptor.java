package cn.backflow.admin.common.secure;

import cn.backflow.admin.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 拦截@Permission注解的方法进行权限验证
 * <p>
 * Created by hunan on 2017/5/21.
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public PermissionInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String request_info = request.getMethod() + " " + request.getRequestURI();
        logger.info(request_info);

        if (handler instanceof HandlerMethod) {
            Permissions permission = getAnnotation((HandlerMethod) handler, Permissions.class);
            if (permission == null) { // 没有权限标注, 直接放行
                return true;
            }
            boolean authorized = authorization(permission.value(), request);
            if (!authorized) {
                logger.info("Unauthorized request: {}, required permission: {}", request_info, permission.value());
                throw new PermissionDeniedException("没有权限访问相应的资源.");
            }
        }
        return true;
    }


    /**
     * 权限验证
     *
     * @param permission 权限标识
     * @param request    {HttpServletRequest}
     * @return authorized
     */
    @SuppressWarnings("unchecked")
    private boolean authorization(final String permission, HttpServletRequest request) throws Exception {

        // 判断当前用户是否登录
        Object user = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
        if (user == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        // TODO add redis implementation
        // Set<String> permissions = redisTemplate.opsForSet().members("");
        List<String> permissions = (List<String>) request.getSession().getAttribute("permissions");

        return permissions != null && permissions.contains(permission);

    }

    private <T extends Annotation> T getAnnotation(HandlerMethod handlerMethod, Class<T> clazz) {
        T annotation = handlerMethod.getMethodAnnotation(clazz);
        if (annotation != null) {
            return annotation;
        }
        return AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), clazz);
    }
}