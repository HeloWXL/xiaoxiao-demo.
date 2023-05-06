package xx.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author halo-king
 * @since 2023-05-05 17:20:40
 */
@SuppressWarnings("serial")
@Data
@TableName("user")
public class User extends Model<User> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("用户ID")
    @ColumnWidth(20)
    private String userId;

    @ExcelProperty("用户名")
    @ColumnWidth(20)
    private String userName;

    @ExcelProperty("年龄")
    @ColumnWidth(20)
    private Integer age;

    @ExcelProperty("地址")
    @ColumnWidth(20)
    private String address;

    @ExcelProperty("创建时间")
    @ColumnWidth(40)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
