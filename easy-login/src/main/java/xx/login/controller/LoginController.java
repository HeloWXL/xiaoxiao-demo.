package xx.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class LoginController {

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
    @GetMapping("doLogin")
    public ModelAndView toPage() {
        return new ModelAndView("Login");
    }
}
