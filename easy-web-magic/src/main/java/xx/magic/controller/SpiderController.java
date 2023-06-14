package xx.magic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import xx.magic.util.SXSProcessorUtil;

@RestController
public class SpiderController {
    @Autowired
    private SXSProcessorUtil sxsProcessorUtil;

    @RequestMapping("/spider")
    public String spider(){
        Spider.create(sxsProcessorUtil).addUrl(SXSProcessorUtil.getURL()).run();
        System.out.println("开始爬取工作信息啦");
        return "sucess";
    }
}
