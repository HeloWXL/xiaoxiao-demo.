package xx.wallpaper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;


import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class HandleProcessor implements PageProcessor {

    @Autowired
    private HandlepipeLine handlepipeLine;

    private String URL = "http://www.netbian.com/meinv/index_2.htm";

    private String PREVIEW_URL = "http://www.netbian.com";


    private Site site = Site.me()
            .setCharset("gbk")//设置编码
            .setTimeOut(10 * 1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3)//设置重试的次数
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")//设置请求头
            ;

    @Override
    public void process(Page page) {
        // 获取壁纸详情连接
        List<String> detailUrlList = page.getHtml().xpath("//div[@id='main']/div[@class='list']/ul/li/a/@href").all();
        if (detailUrlList.size() == 0) {
            saveInfo(page);
        } else {
            for (String detailUrl : detailUrlList) {
                log.info("子页面地址为：[{}]", PREVIEW_URL + detailUrl);
                // 把获取到的url地址放到任务队列中
                page.addTargetRequest(PREVIEW_URL + detailUrl);
            }
        }
    }


    @Override
    public Site getSite() {
        return site;
    }


    //解析页面，获取商品详情信息，保存数据
    private void saveInfo(Page page) {
        Html html = page.getHtml();
        String downloadUrl = html.xpath("//div[@id='main']/div[@class='endpage']/div/p/a/img/@src").toString();
        String wallPaperName = html.xpath("//div[@id='main']/div[@class='action']/h1/text()").toString();
        page.putField("wallPaperName",wallPaperName);
        page.putField("downloadUrl",downloadUrl);
    }


    public void start() {
        Spider.create(new HandleProcessor())
                // 初始访问url地址
                .addUrl(URL)
                .thread(5)
                .addPipeline(this.handlepipeLine)
                .run();
    }
}
