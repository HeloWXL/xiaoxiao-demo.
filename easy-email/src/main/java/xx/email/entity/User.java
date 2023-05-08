package xx.email.entity;

/**
 * @author wangxl
 * @ClassName User
 * @Description TODO
 * @date 2020/12/23 20:10
 */
public class User {
  private String name;
  private Integer num;
  private Double salary;

  public User(String name, Integer num, Double salary) {
    this.name = name;
    this.num = num;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }
}
