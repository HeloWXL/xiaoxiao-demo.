package xx.editor.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xx.editor.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UploadService {

    @Value("${file.path}")
    private String savePath;

    @Value("${server.port:8080}")
    private int port;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public Map<String, Object> uploadFile(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("flag", false);
            result.put("msg", "文件不能为空");
        } else {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            try {
                Date date = new Date();
                // 生成路径
                String relativeDir = FileUtil.getRelativeDir(date);
                // 创建文件路径
                File fileDir = new File(savePath + relativeDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                //新的文件名
                String newName = FileUtil.format(date, "HHmmssSSS") + Math.round(Math.random() * 8999 + 1000) + fileName.substring(fileName.lastIndexOf("."));
                // 新文件路径+新文件名
                String filePath = relativeDir + newName;
                // 保存文件
                file.transferTo(new File(savePath + filePath));
                result.put("flag", true);
                result.put("msg", "上传成功");
                result.put("location", getSysNet("/preview?path=" + filePath));
            } catch (Exception e) {
                e.printStackTrace();
                result.put("flag", false);
                result.put("msg", "图片上传发生未知异常，请联系管理员！");
            }
        }
        return result;
    }

    /**
     * 获取当前访问路径
     *
     * @param path
     * @return
     */
    public String getSysNet(String path) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return String.format("http://%s:%d%s", host, port, path);
    }

    /**
     * 下载文件
     *
     * @param path
     * @param response
     * @throws Exception
     */
    public void downloadFile(String path, HttpServletResponse response) throws Exception {
        response.reset();
        String fileName = "";
        if (path.split("/").length > 0) {
            fileName = path.split("/")[1];
        } else {
            return;
        }
        FileInputStream inputStream = new FileInputStream(new File(savePath + path));
        // 在线打开方式 文件名应该编码成UTF-8
        response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        byte[] buffer = new byte[1024];
        int len;
        OutputStream outputStream = response.getOutputStream();
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        inputStream.close();
    }
}
