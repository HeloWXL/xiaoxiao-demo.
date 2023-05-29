package xx.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xx.notice.common.ConstantUtil;
import xx.notice.dao.UserDao;
import xx.notice.entity.User;
import xx.notice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * (User)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-05 17:20:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public R login(User user, HttpServletRequest request) {
        if (StringUtils.isEmpty(user.getUserId()) || StringUtils.isEmpty(user.getPassword())) {
            return R.failed("用户名密码不能为空");
        }
        User targetUser = this.baseMapper.selectOne(new QueryWrapper<User>().eq("user_id",user.getUserId()));
        if(targetUser==null){
            // 友好提示 ，建议不提示用户名不存在
            return R.failed("用户名密码错误");
        }
         if(targetUser.getPassword().equals(user.getPassword())){
             //让旧的session失效
             HttpSession sessionOld = request.getSession(false);
             if(sessionOld != null){
                 sessionOld.invalidate();
             }
             //重新生成session
             HttpSession session = request.getSession(true);
             session.setAttribute(ConstantUtil.SESSION_KEY,targetUser);
             return R.ok("登录成功");
         }else{
             return R.failed("用户名密码错误");
         }
    }
}
