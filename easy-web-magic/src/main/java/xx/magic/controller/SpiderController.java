package xx.magic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xx.magic.util.HandleProcessor;

import javax.annotation.Resource;

@RestController
public class SpiderController {
    @Resource
    HandleProcessor handleProcessor;

    @GetMapping("start")
    public String start(){
        handleProcessor.process();
        return "succ";
    }
}
