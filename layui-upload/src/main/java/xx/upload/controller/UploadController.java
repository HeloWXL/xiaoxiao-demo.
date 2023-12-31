package xx.upload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xx.upload.entity.Result;
import xx.upload.service.UploadService;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("upload")
public class UploadController {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Resource
    private UploadService uploadService;

    @GetMapping("toPage")
    public ModelAndView toPage() {
        return new ModelAndView("Upload");
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "params",required = false,defaultValue = "") String params) {
        logger.info("携带参数为：[{}]",params);
        return uploadService.uploadFile(file);
    }

    /**
     * 在线预览
     * @param fileId
     * @param response
     */
    @GetMapping("/previewFile")
    public void previewFile(String fileId, HttpServletResponse response) {
        try {
            uploadService.filePreview(fileId, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件
     * @param fileId
     * @param response
     */
    @GetMapping("/downloadFile")
    public void uploadFile(String fileId, HttpServletResponse response) {
        try {
            uploadService.downloadFile(fileId, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
