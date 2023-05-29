package xx.scan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/h5")
public class H5LoginController {
    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping("login")
    public ModelAndView toLogin() {
        return new ModelAndView("h5/Login");
    }


    @GetMapping("home")
    public ModelAndView toHome() {
        return new ModelAndView("h5/Home");
    }
}
