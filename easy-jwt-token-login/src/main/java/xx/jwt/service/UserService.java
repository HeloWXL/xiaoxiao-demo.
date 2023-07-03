package xx.jwt.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import xx.jwt.entity.User;


import javax.servlet.http.HttpServletRequest;

/**
 * (User)表服务接口
 *
 * @author halo-king
 * @since 2023-05-05 17:20:42
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * @param user
     * @return
     */
    R login(User user);

    /**
     * 校验token信息
     * @param token
     * @return
     */
    User checkToken(String token);
}
