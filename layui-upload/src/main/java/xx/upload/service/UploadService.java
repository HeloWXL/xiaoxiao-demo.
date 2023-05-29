package xx.upload.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xx.upload.dao.UploadFileDao;
import xx.upload.entity.Result;
import xx.upload.entity.UploadFile;
import xx.upload.util.FileUtil;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UploadService {

    @Resource
    private UploadFileDao uploadFileDao;

    @Value("${file.path}")
    private String savePath;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public Result uploadFile(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("flag", false);
            result.put("msg", "文件不能为空");
        } else {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            Long fileSize = file.getSize();
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

                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(fileName);
                uploadFile.setFileSize(fileSize);
                uploadFile.setFilePath(filePath);
                uploadFile.setCreateTime(new Date());
                uploadFileDao.insert(uploadFile);

                result.put("flag", true);
                result.put("msg", "上传成功");
                result.put("data", "http://localhost:8082/upload/previewFile?fileId=" + uploadFile.getId());
            } catch (Exception e) {
                e.printStackTrace();
                result.put("flag", false);
                result.put("msg", "图片上传发生未知异常，请联系管理员！");
            }
        }
        return Result.success("success", result);
    }

    /**
     * 文件预览吗
     *
     * @param fileId
     * @param response
     * @throws Exception
     */
    public void filePreview(String fileId, HttpServletResponse response) {
        response.reset();
        UploadFile file = uploadFileDao.selectById(fileId);
        if (file == null) {
            return;
        }
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(savePath + file.getFilePath()));
            // 在线打开方式 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
            byte[] buffer = new byte[1024];
            int len;
            outputStream = response.getOutputStream();
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void downloadFile(String fileId, HttpServletResponse response) {
        response.reset();
        UploadFile file = uploadFileDao.selectById(fileId);
        if (file == null) {
            return;
        }
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(savePath + file.getFilePath()));
            // 下载方式 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
            byte[] buffer = new byte[1024];
            int len;
            outputStream = response.getOutputStream();
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
