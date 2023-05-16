package xx.login.interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 * @author wangxianlin
 */
@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private SystemInterceptor systemInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(systemInterceptor);
        //todo 所有路径都被拦截
        registration.addPathPatterns("/**");
        //todo 添加不拦截路径
        registration.excludePathPatterns(
                //todo 不进行拦截的页面
                "/login","/doLogin"
        );
    }
}
