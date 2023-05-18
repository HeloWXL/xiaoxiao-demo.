package xx.chatroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xx.chatroom.common.ConstantUtil;
import xx.chatroom.entity.User;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("room")
@RestController
public class PageController {

    /**
     * 跳转至聊天室
     * @return
     */
    @GetMapping("index")
    public ModelAndView toPage(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(ConstantUtil.SESSION_KEY);
        return new ModelAndView("/ChatRoom").addObject("user",user);
    }
}
