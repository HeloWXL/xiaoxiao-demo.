package xx.email.entity;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangxl
 * @ClassName MailDto
 * @Description TODO 邮件实体类
 * @date 2020/12/24 9:06
 */
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
  private String recipientStr;
  /**
   * 邮件接收者
   */
  private String[] recipient;
  /**
   * 发送的内容（文本或者HTML）
   */
  private String obj;

  /**
   * 附件集合
   */
  private List<File> files;

  /**
   * 单个附件
   */
  private File file;

  /**
   * 发送一个文件+内容
   * @param subject
   * @param sender
   * @param recipient
   * @param obj
   */
  public MailDto(String subject, String sender, String[] recipient, String obj,File file) {
    this.subject = subject;
    this.sender = sender;
    this.recipient = recipient;
    this.obj = obj;
    this.file = file;
  }

  /**
   * 发送文本（HTML）构造方式
   * @param subject
   * @param sender
   * @param recipient
   * @param obj
   */
  public MailDto(String subject, String sender, String[] recipient, String obj) {
    this.subject = subject;
    this.sender = sender;
    this.recipient = recipient;
    this.obj = obj;
  }

  /**
   * 携带附件（多个）+文本（HTML）发送
   * @param subject
   * @param sender
   * @param recipient
   * @param obj
   * @param files
   */
  public MailDto(String subject, String sender, String[] recipient, String obj,List<File> files) {
    this.subject = subject;
    this.sender = sender;
    this.recipient = recipient;
    this.obj = obj;
    this.files = files;
  }

  public MailDto() {
  }

  public List<File> getFiles() {
    return files;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }
  public String getRecipientStr() {
    return recipientStr;
  }

  public void setRecipientStr(String recipientStr) {
    this.recipientStr = recipientStr;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String[] getRecipient() {
    return recipient;
  }

  public void setRecipient(String[] recipient) {
    this.recipient = recipient;
  }

  public String getObj() {
    return obj;
  }

  public void setObj(String obj) {
    this.obj = obj;
  }

  @Override
  public String toString() {
    return "MailDto{" +
        "subject='" + subject + '\'' +
        ", sender='" + sender + '\'' +
        ", recipientStr='" + recipientStr + '\'' +
        ", recipient=" + Arrays.toString(recipient) +
        ", obj='" + obj + '\'' +
        '}';
  }
}
