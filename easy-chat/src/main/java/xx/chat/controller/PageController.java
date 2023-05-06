package xx.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PageController {


    @GetMapping("toPage")
    public ModelAndView toPage() {
        return new ModelAndView("/chat");
    }

}
