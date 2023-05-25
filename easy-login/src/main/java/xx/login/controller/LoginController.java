package xx.login.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xx.login.entity.User;
import xx.login.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static xx.login.common.ConstantUtil.SESSION_KEY;

@Slf4j
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

    /**
     * 登出功能
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView userlogout(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(SESSION_KEY);
        //session失效
        request.getSession().invalidate();
        if (user != null) {
            log.info(user.getUserName() + "安全退出");
        }
        return new ModelAndView("redirect:login");
    }

}
