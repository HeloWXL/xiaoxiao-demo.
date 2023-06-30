package xx.wallpaper.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class DownLoadUtil {

    /**
     * 下载文件
     *
     * @param urlString
     * @return
     */
    public static String download(String urlString, String rootPath) {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            String filename = rootPath;
            File file = new File(filename);
            os = new FileOutputStream(file, true);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //从微信下载图片时如果没有id对应的图片则下载一个空图片，不会存在返回为null的情况
        return null;
    }
}
