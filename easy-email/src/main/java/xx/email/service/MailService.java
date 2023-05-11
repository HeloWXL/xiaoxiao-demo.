package xx.email.service;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import xx.email.entity.MailDto;
import xx.email.entity.User;
import xx.email.util.MailUtil;
import xx.email.util.Result;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;

@Service
public class MailService {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @Resource
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;


    @Value("${send.from}")
    private String sender;


    /**
     * 发送文本邮件
     *
     * @param mailDto
     * @return
     */
    public Result sendTextMail(MailDto mailDto) {
        getRecipient(mailDto);
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
     * 发送文本邮件
     * @param mailDto
     * @return
     */
    public Result sendHTMLMail(MailDto mailDto) {
        getRecipient(mailDto);
        mailDto.setSender(sender);
        try {
            javaMailSender.send(MailUtil.sendHTMLEmail(javaMailSender,mailDto));
            return Result.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发送失败");
        }
    }


    /**
     * 发送附件
     *
     * @param mailDto
     * @return
     */
    public Result sendAttachFileMail(MailDto mailDto) {
        getRecipient(mailDto);
        mailDto.setSender(sender);
        try {
            MimeMessage mimeMessage = MailUtil.sendAttachFileMail(javaMailSender, mailDto);
            javaMailSender.send(mimeMessage);
            return Result.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发送失败");
        }
    }

    /**
     * Freemarker模板发送
     *
     * @param mailDto
     * @param user
     * @return
     */
    public Result sendFreemarkerMail(MailDto mailDto, User user) {
        String[] recipient = mailDto.getReceiver().split(";");
        StringWriter out = null;
        try {
            out = MailUtil.freeMarkerModel(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
            logger.error("模板识别失败");
        }
        try {
            javaMailSender.send(MailUtil.sendHTMLEmail(javaMailSender, new MailDto(mailDto.getSubject(), sender, recipient, out.toString())));
            return Result.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发送失败");
        }
    }


    public static void getRecipient(MailDto mailDto) {
        String[] recipient = mailDto.getReceiver().split(";");
        mailDto.setRecipient(recipient);
    }
}
