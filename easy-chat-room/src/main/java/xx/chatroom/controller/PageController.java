package xx.chatroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xx.echarts.entity.BarDto;
import xx.echarts.entity.LineDto;
import xx.echarts.entity.PieDto;
import xx.echarts.util.Result;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 跳转至动态图表页面
     * @return
     */
    @GetMapping("dynamic")
    public ModelAndView toDynamicPage() {
        return new ModelAndView("/views/DynamicEchart");
    }

    /**
     * 加载饼图数据
     * @return
     */
    @GetMapping("getPieData")
    public Result getPieData() {
        List<PieDto> data = new ArrayList<>();
        data.add(new PieDto("发布量",100));
        data.add(new PieDto("阅读量",1000));
        data.add(new PieDto("点赞量",200));
        data.add(new PieDto("评论量",120));
        data.add(new PieDto("收藏量",320));
        return Result.success(data);
    }

    /**
     * 加载柱状图
     * @return
     */
    @GetMapping("getBarData")
    public Result getBarData() {
        List<BarDto> data = new ArrayList<>();
        data.add(new BarDto("发布量",100));
        data.add(new BarDto("阅读量",1000));
        data.add(new BarDto("点赞量",200));
        data.add(new BarDto("评论量",120));
        data.add(new BarDto("收藏量",320));
        return Result.success(data);
    }

    @GetMapping("getLineData")
    public Result getLineData() {
        List<LineDto> data = new ArrayList<>();
        data.add(new LineDto("发布量",100));
        data.add(new LineDto("阅读量",1000));
        data.add(new LineDto("点赞量",200));
        data.add(new LineDto("评论量",120));
        data.add(new LineDto("收藏量",320));
        return Result.success(data);
    }
}
