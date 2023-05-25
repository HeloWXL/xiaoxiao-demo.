package xx.notice.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import xx.login.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * (User)表服务接口
 *
 * @author halo-king
 * @since 2023-05-05 17:20:42
 */
public interface UserService extends IService<User> {
    R login(User user, HttpServletRequest request);
}
