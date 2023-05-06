package xx.table.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xx.table.dao.UserDao;
import xx.table.entity.User;
import xx.table.service.UserService;

/**
 * (User)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-05 17:20:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
