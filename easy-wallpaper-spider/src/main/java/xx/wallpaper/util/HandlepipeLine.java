package xx.wallpaper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


@Slf4j
@Component
public class HandlepipeLine implements Pipeline {


    @Override
    public void process(ResultItems resultItems, Task task) {
        String wallPaperName = resultItems.get("wallPaperName");
        String downloadUrl = resultItems.get("downloadUrl");
        log.info("壁纸名称：[{}],下载地址：[{}]",wallPaperName,downloadUrl);
        String savePath = "D:/tmp/"+wallPaperName + ".jpg";
        if(wallPaperName != null|| downloadUrl != null){
            DownLoadUtil.download(downloadUrl,savePath);
        }
    }
}
