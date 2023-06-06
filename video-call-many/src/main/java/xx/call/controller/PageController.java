package xx.call.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PageController {


    /**
     * 跳转至会议
     * @return
     */
    @GetMapping("toMeeting")
    public ModelAndView toMeeting() {
        return new ModelAndView("/Meeting");
    }

    /**
     * 跳转至直播 主播画面
     * @return
     */
    @GetMapping("pubLive")
    public ModelAndView toPubLive() {
        return new ModelAndView("/live/PubLive");
    }

    /**
     * 跳转至直播 用户画面
     * @return
     */
    @GetMapping("subLive")
    public ModelAndView toSubLive() {
        return new ModelAndView("/live/SubLive");
    }
}
