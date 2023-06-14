package xx.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xx.monitor.domain.Server;

/**
 * @Classname MonitorController
 * @Description TODO
 * @Date 2023/6/14 10:36
 * @Created by wangxianlin
 */
@RequestMapping("/")
@RestController
public class MonitorController {
    /**
     * 跳转至图表页面
     * @return
     */
    @GetMapping("index")
    public ModelAndView toPage() {
        return new ModelAndView("/views/Monitor");
    }

    /**
     * 获取系统监控数据
     * @return
     * @throws Exception
     */
    @GetMapping("monitor")
    public Server getServerInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
