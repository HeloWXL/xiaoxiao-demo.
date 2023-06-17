package xx.magic.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import xx.magic.entity.Movie;

import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class HandleProcessor implements PageProcessor {

    @Autowired
    private HandlepipeLine handlepipeLine;

    private String url ="https://dianying.2345.com/list/------.html";

    private Site site = Site.me()
            .setCharset("gb2312")//设置编码
            .setTimeOut(10 * 1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3)//设置重试的次数
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")//设置请求头
            ;

    @Override
    public void process(Page page) {
        List<String> detailUrlList = page.getHtml().xpath("//a[@class='aPlayBtn']/@href").all();
        log.info("子页面地址列表：detailUrlList---->[{}]",detailUrlList.toString());
        if(detailUrlList.size() == 0){
            saveInfo(page);
        }else{
            for (String detailUrl : detailUrlList) {
                log.info("子页面地址为：[{}]",detailUrl);
                // 把获取到的url地址放到任务队列中
                page.addTargetRequest(detailUrl);
            }
        }
    }


    @Override
    public Site getSite() {
        return site;
    }


    //解析页面，获取商品详情信息，保存数据
    private void saveInfo(Page page) {
        Movie movie = new Movie();
        Html html = page.getHtml();
        // 电影名称
        String movieName = html.xpath("//div[@class='tit']/h1/text()").toString();
        movie.setMovieName(movieName.trim());
        // 简介
        String intro = html.xpath("//p[@class='pIntro pShow']/text()").toString();
        if(!StringUtils.hasText(intro)){
            intro = html.xpath("//p[@class='pIntro pHide']/text()").toString();
        }
        movie.setIntro(intro.trim());

        List<Selectable> emTitList = html.xpath("//li[@class='li_3']/div").nodes();
        if(emTitList.size()>0){
            // 导演
            String director = emTitList.get(1).xpath("//a/text()").toString();
            movie.setDirector(director.trim());
            if(emTitList.size()>3){
                // 类型
                String category = emTitList.get(2).xpath("//a/text()").toString();
                movie.setCategory(category.trim());
            }
        }
        // 创建时间
        movie.setCreateTime(new Date());
        page.putField("movieInfo",movie);
    }


    public void process() {
        Spider.create(new HandleProcessor())
                // 初始访问url地址
                .addUrl(url)
                .thread(2)
                .addPipeline(this.handlepipeLine)
                .run();
    }
}
