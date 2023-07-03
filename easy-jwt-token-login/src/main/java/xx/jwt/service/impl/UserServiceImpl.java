package xx.jwt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xx.jwt.dao.UserDao;
import xx.jwt.entity.User;
import xx.jwt.service.UserService;
import xx.jwt.util.JwtUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * (User)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-05 17:20:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    RedisTemplate redisTemplate;


    @Override
    public R login(User user) {
        if (StringUtils.isEmpty(user.getUserId()) || StringUtils.isEmpty(user.getPassword())) {
            return R.failed("用户名密码不能为空");
        }
        User targetUser = this.baseMapper.selectOne(new QueryWrapper<User>().eq("user_id", user.getUserId()));
        if (targetUser == null) {
            // 友好提示 ，建议不提示用户名不存在
            return R.failed("用户名密码错误");
        }
        if (targetUser.getPassword().equals(user.getPassword())) {
            String token = JwtUtils.createToken(user.getId());
            redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
            return R.ok(token);
        } else {
            return R.failed("用户名密码错误");
        }
    }

    @Override
    public User checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Map<String, Object> map = JwtUtils.checkToken(token);
        if (map == null) {
            return null;
        }
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }
}
