package xx.chatroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("room")
@RestController
public class PageController {

    /**
     * 跳转至聊天室
     * @return
     */
    @GetMapping("index")
    public ModelAndView toPage() {
        return new ModelAndView("/ChatRoom");
    }
}
