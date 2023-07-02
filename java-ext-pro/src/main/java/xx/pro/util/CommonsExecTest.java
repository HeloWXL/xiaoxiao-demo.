package xx.pro.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CommonsExecTest {
    public static void main(String[] args) {
        CommandLine cmdLine = CommandLine.parse("java -version");

        DefaultExecutor executor = new DefaultExecutor();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);
        String output = null;
        try {
            executor.execute(cmdLine);
            output = outputStream.toString("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(output);
    }
}
