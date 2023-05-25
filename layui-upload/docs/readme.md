# Layui 文件上传

##  文件下载

文件下载有两种形式，一种是直接在页面打开，一种是以附件形式下载

```java
// 4. 附件下载 attachment 附件 inline 在线打开(默认值)
// 在线打开
response.setHeader("content-disposition", inline + ";fileName=" + fileName);
// 附件下载
response.setHeader("content-disposition", attachment + ";fileName=" + fileName);

```


