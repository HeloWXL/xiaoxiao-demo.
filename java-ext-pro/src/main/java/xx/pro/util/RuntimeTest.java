package xx.pro.util;

import java.io.InputStream;
import java.io.InputStreamReader;

public class RuntimeTest {
    public static void main(String[] args) {
        try {
            //获取执行进程
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("ipconfig");

            //读取输入流
            InputStream inputStream = process.getInputStream();
            //将字节流转成字符流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            //字符缓冲区
            char[] c = new char[1024];
            int len = -1;
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                System.out.print(s);
            }
            inputStream.close();
            inputStreamReader.close();
            //阻塞当前线程，直到进程退出为止
            process.waitFor();
            int exitValue = process.exitValue();
            if (exitValue == 0) {
                System.out.println("进程正常结束");
            } else {
                System.out.println("进程异常结束");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
