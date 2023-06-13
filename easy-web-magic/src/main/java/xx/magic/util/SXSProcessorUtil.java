package xx.magic.util;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Random;

@Component()
public class SXSProcessorUtil implements PageProcessor {
    //给定要爬取的网址 这块审核过不了
    static String URL = "https://www.xiaohongshu.com/explore";

    /**
     * 获取site实例
     * setRetryTimes(3)重试3次
     * setCharset("utf-8")字符集编码为utf-8
     * setSleepTime(new Random().nextInt(20) * 100)页面等待，时间随机在0-2s之内
     */
    private Site site = Site.me()
            .setRetryTimes(3)
            .setCharset("utf-8")
            .setSleepTime(new Random().nextInt(20) * 100);

    /**
     * 输出URL
     * @return
     */
    public static String getURL() {
        return URL;
    }


    /**
     *  爬取页面解析位置
     * @param page
     */
    @Override
    public void process(Page page) {

        //获取页面的全部代码，开始调试时可以打开这块，查看能否爬取到页面的代码
         String s1 = page.getHtml().get();
        System.out.println(s1);

        //爬取招聘页面解析
        //根据需要提取的html页面分析,得到想要的信息的xpath(这里需要你会在浏览器页面中会定位元素)
        //这个list中存储的就是每个招聘信息的内容，然后遍历信息，在这些信息中找自己想要的数据，得到xpath，获取到信息

        List<String> node = page.getHtml().xpath("//a[@class=\"postTitle2 vertical-middle\"]/a/text()").all();
        System.out.println(node);
        return;
    }

    /**
     * 获取实例
     * @return
     */
    @Override
    public Site getSite() {
        return site;
    }
}
