package xx.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xx.login.dao.UserDao;
import xx.login.entity.User;
import xx.login.service.UserService;

/**
 * (User)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-05 17:20:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
