package xx.email.entity;

import lombok.Data;

/**
 * @author wangxl
 * @ClassName User
 * @Description TODO
 * @date 2020/12/23 20:10
 */
@Data
public class User {
  /**
   * 名称
   */
  private String name;
  /**
   * 数量
   */
  private Integer num;
  /**
   * 薪资
   */
  private Double salary;

  public User(String name, Integer num, Double salary) {
    this.name = name;
    this.num = num;
    this.salary = salary;
  }
}
