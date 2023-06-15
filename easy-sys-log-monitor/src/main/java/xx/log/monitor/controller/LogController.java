package xx.log.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @GetMapping("a")
    public String test(){
        log.error("hello");
        log.debug("hello");
        log.info("hello");
        log.warn("hello");
        return "ss";
    }
}
