package xx.chatroom.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xx.chatroom.entity.User;
import xx.chatroom.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class LoginController {

    @Resource
    UserService userService;

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping("login")
    public ModelAndView toLogin() {
        return new ModelAndView("Login");
    }


    /**
     * 登录
     * @return
     */
    @PostMapping("doLogin")
    public R doLogin(User user, HttpServletRequest request) {
        return  userService.login(user,request);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("doRegister")
    public R doRegister(@RequestBody User user) {
        return  R.ok(userService.save(user));
    }
}
