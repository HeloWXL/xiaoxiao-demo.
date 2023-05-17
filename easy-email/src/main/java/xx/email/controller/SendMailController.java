package xx.email.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import xx.email.entity.MailDto;
import xx.email.entity.User;
import xx.email.service.MailService;
import xx.email.util.MultipartFileToFile;
import xx.email.util.Result;

import javax.annotation.Resource;
import java.io.File;

@RestController
@RequestMapping("mail")
public class SendMailController {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(SendMailController.class);

    @Resource
    private MailService mailService;


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
     * 发送文本邮件
     *
     * @param mailDto
     * @return
     */
    @PostMapping("sendText")
    public Result sendSimpleMail(@RequestBody MailDto mailDto) {
        return mailService.sendTextMail(mailDto);
    }


    /**
     * 发送HTMl 片段
     *
     * @param mailDto
     * @return
     */
    @PostMapping("sendHTML")
    public Result sendHTML(@RequestBody MailDto mailDto) {
        return mailService.sendHTMLMail(mailDto);
    }

    /**
     * 发送附件邮件
     *
     * @param mailDto
     * @return
     */
    @PostMapping("sendFile")
    public Result sendFileMail(MailDto mailDto, MultipartFile multipartFile) {
        try {
            File file = MultipartFileToFile.multipartFileToFile(multipartFile);
            if (file == null) {
                return Result.fail("发送失败");
            }
            mailDto.setFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("MultipartFile To File ERROR");
        }
        return mailService.sendAttachFileMail(mailDto);
    }

    /**
     * Freemarker模板发送
     *
     * @param mailDto
     * @param user
     * @return
     */
    @PostMapping("sendFreemarkerMail")
    public Result sendFreemarkerMail(MailDto mailDto, User user) {
        return mailService.sendFreemarkerMail(mailDto, user);
    }
}
