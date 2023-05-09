package xx.editor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xx.editor.service.UploadService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class PageController {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(PageController.class);

    @Resource
    private UploadService uploadService;

    @GetMapping("toPage")
    public ModelAndView toPage() {
        return new ModelAndView("/views/Editor");
    }


    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/uploadImg")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        return uploadService.uploadFile(file);
    }

    /**
     * 如果上传的文件有存储记录，可通过文件记录ID查询路径在访问
     * 下载文件
     * @param path
     * @param response
     */
    @GetMapping("/preview")
    public void uploadFile(String path, HttpServletResponse response) {
        try {
            uploadService.downloadFile(path, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
