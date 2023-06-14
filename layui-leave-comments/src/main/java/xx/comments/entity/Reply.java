package xx.comments.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * (Reply)表实体类
 *
 * @author halo-king
 * @since 2023-05-10 15:13:57
 */
@SuppressWarnings("serial")
@Data
public class Reply extends Model<Reply> {
    //回复主键
    private Integer replyId;
    //留言ID
    private Integer leaveId;
    //用户ID
    private Integer userId;
    //回复内容
    private String content;
    //回复时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
