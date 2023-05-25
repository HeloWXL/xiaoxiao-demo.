# Layui 文件上传

## 文件上传

如果不是图片的文件上传，可不展示上传后的图片和预览的图片

### 普通图片上传

```js
 upload.render({
    elem: '#test1'
    , url: '/upload/uploadFile' //此处用的是第三方的 http 请求演示，实际使用时改成您自己的上传接口即可。
    , before: function (obj) {
        //预读本地文件示例，不支持ie8
        obj.preview(function (index, file, result) {
            $('#demo1').attr('src', result); //图片链接（base64）
        });
        element.progress('demo', '0%'); //进度条复位
        layer.msg('上传中', {icon: 16, time: 0});
    }
    , done: function (res) {

        if (res.data.flag === true) {
            layer.msg('上传成功', {icon: 1}, () => {
                $('#demoText').html(''); //置空上传失败的状态
                table.reload('fileList')
            });
        } else {
            layer.msg('上传失败', {icon: 1})
        }
    }
    //进度条
    , progress: function (n, elem, e) {
        element.progress('demo', n + '%'); //可配合 layui 进度条元素使用
        if (n == 100) {
            layer.msg('上传完毕', {icon: 1});
        }
    }
});
```

### 拖拽上传

```js
 upload.render({
    elem: '#test10'
    , url: '/upload/uploadFile' //此处用的是第三方的 http 请求演示，实际使用时改成您自己的上传接口即可。
    , done: function (res) {
        if (res.data.flag === true) {
            layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.data.data);
            layer.msg('上传成功', {icon: 1}, () => {
                table.reload('fileList')
            });
        }
    }
});
```

### 选完文件后不自动上传

```js
  //选完文件后不自动上传
upload.render({
    elem: '#test8'
    , url: '/upload/uploadFile' //此处配置你自己的上传接口即可
    , auto: false
    //,multiple: true
    , bindAction: '#test9'
    , done: function (res) {
        layer.msg('上传成功');
        table.reload('fileList')
    }
});
```

### 原始文件上传

```js
 upload.render({
    elem: '#test20'
    , url: '/upload/uploadFile' //此处配置你自己的上传接口即可
    , done: function (res) {
        if (res.data.flag === true) {
            layer.msg('上传成功', {icon: 1}, () => {
                table.reload('fileList')
            });
        }
    }
});
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

> 在线预览



> 下载


