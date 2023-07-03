package xx.jwt.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xx.jwt.entity.User;
import xx.jwt.service.UserService;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

    @Resource
    UserService userService;


    /**
     * 登录
     *
     * @return
     */
    @PostMapping("doLogin")
    public R doLogin(User user) {
        return userService.login(user);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("doRegister")
    public R doRegister(@RequestBody User user) {
        return R.ok(userService.save(user));
    }

}
