package xx.login.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xx.login.entity.User;
import xx.login.service.UserService;

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

    /**
     * 登出功能
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R userlogout(HttpServletRequest request){
//        if(request==null){
//            throw new BusinessException(ErrorCode.NOT_LOGIN,"该用户没有登录");
//        }
//        int result = userService.userLogout(request);
//        return R.success(result);
        return R.ok("");
    }

}
