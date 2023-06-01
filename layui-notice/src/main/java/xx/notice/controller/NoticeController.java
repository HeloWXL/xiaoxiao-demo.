package xx.notice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("notice")
public class NoticeController {

    /**
     * 跳转通知页面
     * @return
     */
    @GetMapping("toIndex")
    public ModelAndView toIndex() {
        return new ModelAndView("Notice");
    }




}
