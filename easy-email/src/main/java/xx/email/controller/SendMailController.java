package xx.email.controller;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import xx.email.entity.MailDto;
import xx.email.entity.User;
import xx.email.util.MailUtil;
import xx.email.util.MultipartFileToFile;
import xx.email.util.Result;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@RestController
@RequestMapping("mail")
public class SendMailController {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(SendMailController.class);

    @Resource
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${send.from}")
    private String sender;

    /**
     * 发送邮件页面
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView toSendView() {
        return new ModelAndView("/index").addObject("sender", sender);
    }

    /**
     * 发送文本
     */
    @PostMapping("sendText")
    public Result sendSimpleMail(@RequestBody MailDto mailDto) {
        String[] recipient = mailDto.getReceiver().split(";");
        mailDto.setRecipient(recipient);
        mailDto.setSender(sender);
        try {
            javaMailSender.send(MailUtil.sendEmailText(mailDto));
            return Result.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发送失败");
        }
    }

    /**
   *   Freemarker模板发送
   * @throws MessagingException
   * @throws IOException
   * @throws TemplateException
   */
  @PostMapping("sendFreemarkerMail")
  public Result sendFreemarkerMail( MailDto mailDto,  User user) throws TemplateException, IOException  {
    String[] recipient = mailDto.getReceiver().split(";");
    StringWriter out = MailUtil.freeMarkerModel(user);
    try{
        javaMailSender.send(MailUtil.sendEmailHTML(javaMailSender,new MailDto(mailDto.getSubject(),sender,recipient,out.toString())));
        return Result.success("发送成功");
    }catch (Exception e) {
        e.printStackTrace();
        return Result.fail("发送失败");
    }
  }



//  /**
//   * 携带附件发送
//   * @throws MessagingException
//   */
//  @PostMapping("sendAttachFileMail")
//  public void sendAttachFileMail(MailDto mailDto) throws MessagingException {
//    String[] recipient = mailDto.getReceiver().split(";");
//    mailDto.setRecipient(recipient);
//    mailDto.setSender(sender);
//    javaMailSender.send(MailUtil.sendAttachFileMail(javaMailSender,new MailDto("HelloWorld","756316064@qq.com",recipient,"我爱你呀","helloWorld.jpg","C:\\Users\\王咸林\\Documents\\bianhua2\\demo.jpg")));
//  }



//
//  /**
//   * 发送HTML+附件
//   * @throws MessagingException
//   */
//  @PostMapping("sendImgResMail")
//  public void sendImgResMail(MultipartFile[] frontEndFiles,MailDto mailDto) throws MessagingException {
////    String[] recipient = new String[]{"3209132078@qq.com"};
////    String html = "<p>hello 大家好，这是一封测试邮件，这封邮件包含两种图片，分别如下</p><p>第一张图片：</p><img src='cid:p01'/><p>第二张图片：</p><img src='cid:p02'/>";
////    Map<String,String> map = new HashMap<>();
//    System.out.println(mailDto.toString());
//    for (int i = 0 ;i<frontEndFiles.length;i++){
//      System.out.println(frontEndFiles[i].getName());//files
//      System.out.println(frontEndFiles[i].getOriginalFilename());//QQ截图20201228171525.png
//      System.out.println(frontEndFiles[i].getContentType());//image/png
//      System.out.println(frontEndFiles[i].getResource());//MultipartFile resource [files]
//      File file = null;
//      try {
//        file = MultipartFileToFile.multipartFileToFile(frontEndFiles[i]);
//        System.out.println(file.getName());//QQ截图20201228171525.png
//        System.out.println(file.getAbsolutePath());//D:\wangxianlin\boot-mail\QQ截图20201228171525.png
//      } catch (Exception e) {
//        e.printStackTrace();
//        System.out.println("文件转换异常");
//      }
//      MultipartFileToFile.delteTempFile(file);
//    }
//
////    map.put("p01","C:\\Users\\王咸林\\Documents\\bianhua2\\demo.jpg");
////    map.put("p02","C:\\Users\\王咸林\\Documents\\bianhua2\\test.jpg");
////    javaMailSender.send(MailUtil.sendImgResMail(javaMailSender,new MailDto("HelloWorld","756316064@qq.com",recipient,html,map)));
//  }
//


  /**
   * todo Thymeleaf模板发送
   * @throws MessagingException
   */
  @PostMapping("sendThymeleafMail")
  public Result sendThymeleafMail(MailDto mailDto,User user) {
    String[] recipient = mailDto.getReceiver().split(";");
    Context context = new Context();
    context.setVariable("username", user.getName());
    context.setVariable("num",user.getNum());
    context.setVariable("salary", user.getSalary());
    String process = templateEngine.process("mail.html", context);
    try{
        javaMailSender.send(MailUtil.sendEmailHTML(javaMailSender,new MailDto(mailDto.getSubject(),sender,recipient,process)));
        return Result.success("发送成功");
    }catch (Exception e){
        e.printStackTrace();
        return Result.fail("发送失败");
    }
  }
}
