# Java 调用外部程序

# Runtime类与ProcessBuilder类

> 使用Runtime类：

```java
Runtime runtime=Runtime.getRuntime();
Process process=runtime.exec("外部程序路径");
```

> 使用ProcessBuilder类

```java
ProcessBuilder builder=new ProcessBuilder("外部程序路径");
Process process=builder.start();

```

# 参数传递

以上两种方法均可启动一个外部进程。如果需要向外部进程传递参数，可以在exec() 或ProcessBuilder构造函数中传递字符串数组;

````java

String[]cmdarray={"外部程序路径","参数1","参数2"};
        Process process=runtime.exec(cmdarray);


````

# 执行结果

外部进程的返回结果可以通过process对象获取。可以使用getInputStream() 方法读取外部进程的标准输出流，或者使用getErrorStream()
方法读取错误输出流。

```java
InputStream inputStream=process.getInputStream(); // 标准输出流
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

        String line;
        while((line=bufferedReader.readLine())!=null){
        System.out.println(line); // 输出每行结果
        }

```

注意：在调用外部程序时一定要小心，因为它可能会引起安全问题和系统崩溃等异常情况。建议使用该功能时对外部程序进行充分的测试和验证

# Runtime类的使用

```java
    @Test
public void runtimeTest(){
        try{
        //获取执行进程
        Runtime runtime=Runtime.getRuntime();
        Process process=runtime.exec("ipconfig");

        //读取输入流
        InputStream inputStream=process.getInputStream();
        //将字节流转成字符流
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"gbk");
        //字符缓冲区
        char[]c=new char[1024];
        int len=-1;
        while((len=inputStreamReader.read(c))!=-1){
        String s=new String(c,0,len);
        System.out.print(s);
        }
        inputStream.close();
        inputStreamReader.close();
        //阻塞当前线程，直到进程退出为止
        process.waitFor();
        int exitValue=process.exitValue();
        if(exitValue==0){
        System.out.println("进程正常结束");
        }else{
        System.out.println("进程异常结束");
        }
        }catch(Exception e){
        e.printStackTrace();
        }
        }

```

# ProcessBuilder类的使用

> 无参数调用

```java
    @Test
public void processBuilderTest1(){
        ProcessBuilder processBuilder=new ProcessBuilder();
        processBuilder.command("java");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try{
        //启动进程
        Process start=processBuilder.start();
        //获取输入流
        InputStream inputStream=start.getInputStream();
        //转成字符输入流
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"gbk");
        int len=-1;
        //字符缓冲区
        char[]c=new char[1024];
        //读取进程输入流中的内容
        while((len=inputStreamReader.read(c))!=-1){
        String s=new String(c,0,len);
        System.out.print(s);
        }
        inputStreamReader.close();
        inputStream.close();
        //阻塞当前线程，直到进程退出为止
        start.waitFor();
        int exitValue=start.exitValue();
        if(exitValue==0){
        System.out.println("进程正常结束");
        }else{
        System.out.println("进程异常结束");
        }
        }catch(IOException e){
        e.printStackTrace();
        }
        }

```

> 简单参数调用

```java
processBuilder.command("java","-version");

```

> 复杂参数调用

```java
 List<String> command=new ArrayList<>();
        command.add("java");
        command.add("-version");
        processBuilder.command(command);
```


# Apache Commons Exec库


Apache Commons Exec 是一个用于执行外部进程的 Java 库，可以方便地启动和控制进程，并且提供了对输入、输出流的管理和处理


## 使用步骤介绍

1.添加依赖，将Apache Commons Exec库添加到项目中



````java

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-exec</artifactId>
            <version>1.3</version>
        </dependency>

````


2.构造命令行对象：通过 CommandLine 对象构造需要执行的外部程序和参数。


```java
CommandLine cmdLine = CommandLine.parse("command argument1 argument2");

```

3.创建执行器对象：通过 DefaultExecutor 对象创建一个执行器，并设置工作目录（可选）。

```java
DefaultExecutor executor = new DefaultExecutor();
executor.setWorkingDirectory(new File("/your/workdir"));

```

4.创建处理器对象：PumpStreamHandler 对象可以处理输入输出流并存储进程的标准输出和标准错误信息。

```java
ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
executor.setStreamHandler(streamHandler);

```


5.执行命令并等待进程结束：调用 executor.execute方法执行命令。如果要等待进程执行完成对应的操作，请在此后调用 process.waitFor() 等待进程结束。执行完成后处理 outputStream 中的输出结果。


```java
executor.execute(cmdLine);
String output = outputStream.toString("UTF-8");
System.out.println(output);

```


## 使用实例

```java
    public static void main(String[] args) throws IOException {
        CommandLine cmdLine = CommandLine.parse("java -version");

        DefaultExecutor executor = new DefaultExecutor();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);

        executor.execute(cmdLine);
        String output = outputStream.toString("UTF-8");
        System.out.println(output);
    }

```

> 输出结果

```shell
java version "1.8.0_271"
Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)

```