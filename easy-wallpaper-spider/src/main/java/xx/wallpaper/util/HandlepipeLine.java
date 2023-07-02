package xx.wallpaper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;


@Slf4j
@Component
public class HandlepipeLine implements Pipeline {

    @Resource
    DownLoadUtil downLoadUtil;

    @Value("${file.savePath}")
    String savePath;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String wallPaperName = resultItems.get("wallPaperName");
        String downloadUrl = resultItems.get("downloadUrl");
        log.info("壁纸名称：[{}],下载地址：[{}]", wallPaperName, downloadUrl);
        String path = savePath + wallPaperName + ".jpg";
        if (wallPaperName != null || downloadUrl != null) {
            downLoadUtil.download(downloadUrl, path);
        }
    }
}
