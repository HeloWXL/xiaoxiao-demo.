package xx.rabbitmq.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@RestController
public class PageController {

    @GetMapping("/index")
    public ModelAndView toIndex(){
        return new ModelAndView("/client");
    }
}
