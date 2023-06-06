package xx.comments.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * (Leave)表实体类
 *
 * @author halo-king
 * @since 2023-05-10 15:12:17
 */
@SuppressWarnings("serial")
@Data
@TableName("leave_comment")
public class Leave extends Model<Leave> {
    //留言主键
    @TableId(type = IdType.AUTO)
    private Integer leaveId;
    //用户ID
    private Integer userId;
    //留言内容
    private String content;
    //留言时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
