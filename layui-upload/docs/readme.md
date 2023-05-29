# Layui 文件上传


# 项目启动地址：
http://localhost:8082/upload/toPage

# 可能出现的问题

```text
org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException: The field file exceeds its maximum permitted size of 1048576 bytes.
```
修改application.yml 文件下配置即可。
```yml
  #文件上传限制
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
```


## 文件下载

文件下载有两种形式，一种是直接在页面打开，一种是以附件形式下载

```java
// 4. 附件下载 attachment 附件 inline 在线打开(默认值)
// 在线打开
response.setHeader("content-disposition",inline+";fileName="+fileName);
// 附件下载
response.setHeader("content-disposition",attachment+";fileName="+fileName);
```

