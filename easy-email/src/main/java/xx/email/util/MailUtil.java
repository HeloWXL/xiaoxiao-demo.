package xx.email.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;
import xx.email.EasyEmailApplication;
import xx.email.entity.MailDto;
import xx.email.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author wangxl
 * @ClassName MailUtil
 * @Description TODO
 * @date 2020/12/23 20:37
 */

public class MailUtil {

  /**
   * todo 发送文本
   * @param mail
   * @return
   */
  public static SimpleMailMessage sendEmailText(MailDto mail){
      SimpleMailMessage message = new SimpleMailMessage();
      message.setSubject(mail.getSubject());
      message.setFrom(mail.getSender());
      message.setTo(mail.getRecipient());
      message.setSentDate(new Date());
      message.setText(mail.getContent());
      return message;
  }

  /**
   * todo 发送附件+文本
   * @param javaMailSender
   * @param mail
   * @return
   * @throws MessagingException
   */
  public static MimeMessage sendAttachFileMail(JavaMailSender javaMailSender,MailDto mail) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
    helper.setSubject(mail.getSubject());
    helper.setFrom(mail.getSender());
    helper.setTo(mail.getRecipient());
    helper.setSentDate(new Date());
    helper.setText(mail.getContent());
    helper.addAttachment(mail.getFile().getName(),mail.getFile());
    return mimeMessage;
  }

  /**
   * todo 发送HTML
   * @param javaMailSender
   * @param mail
   * @return
   * @throws MessagingException
   */
  public static MimeMessage sendHTMLEmail(JavaMailSender javaMailSender,MailDto mail) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setSubject(mail.getSubject());
    helper.setFrom(mail.getSender());
    helper.setTo(mail.getRecipient());
    helper.setSentDate(new Date());
    helper.setText(mail.getContent(),true);
    return mimeMessage;
  }




  /**
   * todo 发送多个附件
   * @param javaMailSender
   * @param mail
   * @return
   * @throws MessagingException
   */
  public static MimeMessage sendImgResMail(JavaMailSender javaMailSender, MailDto mail) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setSubject(mail.getSubject());
    helper.setFrom(mail.getSender());
    helper.setTo(mail.getRecipient());
    helper.setSentDate(new Date());
    helper.setText(mail.getContent(),true);
    for (File f:mail.getFiles()){
      helper.addInline(f.getName(),new FileSystemResource(f));
    }
    return mimeMessage;
  }

  /**
   * todo  freeMarker模板配置,如需修改模板，修改resources/templates/mail.ftl
   * @param user
   * @return
   * @throws IOException
   * @throws TemplateException
   */
  public static StringWriter freeMarkerModel(User user) throws IOException, TemplateException {
    //构建 Freemarker 的基本配置
    Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
    // 配置模板位置
    ClassLoader loader = EasyEmailApplication.class.getClassLoader();
    configuration.setClassLoaderForTemplateLoading(loader, "templates");
    //加载模板
    Template template = configuration.getTemplate("mail.ftl");
    StringWriter out = new StringWriter();
    //模板渲染，渲染的结果将被保存到 out 中 ，将out 中的 html 字符串发送即可
    template.process(user, out);
    return out;
  }


  /**
   * 将MultipartFile转换成 File
   * @param multipartFile
   * @return
   */
  public static File transferToFile(MultipartFile multipartFile) {
    //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
    File file = null;
    try {
      String originalFilename = multipartFile.getOriginalFilename();
      String[] filename = originalFilename.split(".");
      file=File.createTempFile(filename[0], filename[1]);
      multipartFile.transferTo(file);
      file.deleteOnExit();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
  }
}
