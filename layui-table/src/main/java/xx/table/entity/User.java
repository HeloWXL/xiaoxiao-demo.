package xx.table.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


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

    private String userId;

    private String userName;

    private Integer age;

    private String address;

    private Date createTime;
}
