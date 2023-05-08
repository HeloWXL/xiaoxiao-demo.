package xx.echarts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("echart")
@RestController
public class PageController {

    /**
     * 跳转至图表页面
     * @return
     */
    @GetMapping("index")
    public ModelAndView toPage() {
        return new ModelAndView("/views/Echarts");
    }

}
