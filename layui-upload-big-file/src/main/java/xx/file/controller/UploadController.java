package xx.file.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xx.file.service.UploadService;
import xx.file.util.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("upload")
public class UploadController {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    @GetMapping("toPage")
    public ModelAndView toPage() {
        return new ModelAndView("Upload");
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "params", required = false, defaultValue = "") String params) {
        logger.info("携带参数为：[{}]", params);
        return uploadService.uploadFile(file);
    }

    /**
     * 在线预览
     *
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
     *
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


    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${file.path}")
    private String savePath;

    private Map<String, List<File>> chunksMap = new ConcurrentHashMap<>();


    @PostMapping("/upload")
    public void upload(@RequestParam int currentChunk, @RequestParam int totalChunks,
                       @RequestParam MultipartFile chunk, @RequestParam String fileName) throws IOException {

        // 将分片保存到临时文件夹中
        String chunkName = chunk.getOriginalFilename() + "." + currentChunk;
        File chunkFile = new File(savePath, chunkName);
        chunk.transferTo(chunkFile);

        // 记录分片上传状态
        List<File> chunkList = chunksMap.get(fileName);
        if (chunkList == null) {
            chunkList = new ArrayList<>(totalChunks);
            chunksMap.put(fileName, chunkList);
        }
        chunkList.add(chunkFile);
    }

    @PostMapping("/merge")
    public String merge(@RequestParam String fileName) throws IOException {
        // 获取所有分片，并按照分片的顺序将它们合并成一个文件
        List<File> chunkList = chunksMap.get(fileName);
        if (chunkList == null || chunkList.size() == 0) {
            throw new RuntimeException("分片不存在");
        }

        File outputFile = new File(savePath, fileName);
        try (FileChannel outChannel = new FileOutputStream(outputFile).getChannel()) {
            for (int i = 0; i < chunkList.size(); i++) {
                try (FileChannel inChannel = new FileInputStream(chunkList.get(i)).getChannel()) {
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                }
                // 删除分片
                chunkList.get(i).delete();
            }
        }
        // 删除记录
        chunksMap.remove(fileName);
        // 获取文件的访问URL
        Resource resource = resourceLoader.getResource("file:" + savePath + fileName); //由于是本地文件，所以开头是"file"，如果是服务器，请改成自己服务器前缀
        return resource.getURI().toString();
    }
}
