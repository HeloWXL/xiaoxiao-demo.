package xx.email.entity;

import lombok.Data;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangxl
 * @ClassName MailDto
 * @Description TODO 邮件实体类
 * @date 2020/12/24 9:06
 */
@Data
public class MailDto {

  /**
   * 邮件主题
   */
  private String subject;

  /**
   * 邮件发送者
   */
  private String sender;

  /**
   * 邮件接收者
   */
  private String receiver;
  /**
   * 邮件接收者
   */
  private String[] recipient;
  /**
   * 发送的内容（文本或者HTML）
   */
  private String content;

  /**
   * 附件集合
   */
  private List<File> files;

  /**
   * 单个附件
   */
  private File file;


  public MailDto(String subject, String sender, String[] recipient,String content) {
    this.subject = subject;
    this.sender = sender;
    this.recipient = recipient;
    this.content = content;
  }
}
