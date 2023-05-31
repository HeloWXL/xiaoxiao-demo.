package xx.login.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xx.login.common.ConstantUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Object object = request.getSession().getAttribute(ConstantUtil.SESSION_KEY);
        //不存在的话，跳转到登录界面
        if (object == null) {
            String defaultFailureUrl = "/login";
            log.info("被拦截地址-->" + request.getRequestURI());
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            log.info("base Path:[{}]",basePath);
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
            builder.append("alert(\"页面过期，请重新登录\");");
            builder.append("window.top.location.href=\"");
            builder.append(defaultFailureUrl);
            builder.append("\";</script>");
            out.print(builder.toString());
            out.close();
            return false;
        } else {
            //存在session，放行通过
            return true;
        }
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
