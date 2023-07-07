package xx.student.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author halo-king
 * @since 2023-07-07 15:34:44
 */
public class Student implements Serializable{
    private static final long serialVersionUID = -92741906024334010L;

    private Integer id;
    /**
     * 学号
     */
    private String stuNo;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 入学时间
     */
    private Object joinTime;
    /**
     * 创建时间
     */
    private Date createTime;
}
