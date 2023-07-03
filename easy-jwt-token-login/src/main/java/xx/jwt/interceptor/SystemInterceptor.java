package xx.jwt.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xx.jwt.entity.User;
import xx.jwt.service.UserService;
import xx.jwt.util.UserThreadLocal;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现拦截器，防止直接URL未登录的访问（但排除个别）
 *
 * @author lmwang
 * 2013-10-16 上午11:46:12
 */
@Component
@Slf4j
public class SystemInterceptor implements HandlerInterceptor {

    /**
     * 默认构造器
     */
    public SystemInterceptor() {
    }

    @Autowired
    private UserService userService;

    /**
     * 预处理器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");

        log.info("===============request start===============");
        log.info("request uri:{}", request.getRequestURI());
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("===============request end===============");

        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("token 为空");
        }
        User user = userService.checkToken(token);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView mav) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object obj, Exception err)
            throws Exception {
    }
}
