package xx.comments.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Reply)表实体类
 *
 * @author halo-king
 * @since 2023-05-10 15:13:57
 */
@SuppressWarnings("serial")
public class Reply extends Model<Reply> {
    //回复主键
    private Integer replyId;
    //留言ID
    private Integer leaveId;
    //用户ID
    private String userId;
    //回复内容
    private String content;
    //回复时间
    private Date createTime;


    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.replyId;
    }
}
