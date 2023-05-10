package xx.comments.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Leave)表实体类
 *
 * @author halo-king
 * @since 2023-05-10 15:12:17
 */
@SuppressWarnings("serial")
public class Leave extends Model<Leave> {
    //留言主键
    private Integer leaveId;
    //用户ID
    private Integer userId;
    //留言内容
    private String content;
    //留言时间
    private Date createTime;


    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
        return this.leaveId;
    }
}
