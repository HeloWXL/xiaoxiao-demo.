package xx.call.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PageController {

    /**
     * 跳转至一对一视频通话
     * @return
     */
    @GetMapping("toCall")
    public ModelAndView toCall() {
        return new ModelAndView("/call");
    }

    /**
     * 跳转至会议
     * @return
     */
    @GetMapping("toMeeting")
    public ModelAndView toMeeting() {
        return new ModelAndView("/Meeting");
    }

    /**
     * 跳转至直播
     * @return
     */
    @GetMapping("toLive")
    public ModelAndView toLive() {
        return new ModelAndView("/Live");
    }
}
