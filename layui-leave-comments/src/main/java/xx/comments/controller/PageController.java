package xx.comments.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("leave")
public class PageController {

    @GetMapping("index")
    public ModelAndView tIndex() {
        return new ModelAndView("Leave");
    }
}
