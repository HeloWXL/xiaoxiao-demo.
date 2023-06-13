package xx.notice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 与“通知”本身相关的具体信息，包括通知的内容、通知的类型和通知的对象类型等(NoteSource)表实体类
 *
 * @author halo-king
 * @since 2023-06-13 14:17:18
 */
@SuppressWarnings("serial")
@Data
public class NoteSource extends Model<NoteSource> {
    //通知的主键
    @TableId(type = IdType.AUTO)
    private Long noteId;
    //通知的具体内容
    private String noteContent;
    //通知的类型（0：实时通知；1：登录时通知；2：定时通知）
    private Integer noteType;
    //通知的接受对象类型（0：所有员工；1：按指定分组；2：按指定个人）
    private Integer acceptType;
    //通知的具体接受对象（分组主键或接收人主键或all）
    private String acceptObj;
    //定时通知的时间（仅通知类型是定时通知时有效）
    private Date regularTime;
    //当前通知的创建时间
    private Date createTime;
    //当前通知的创建人（主键）
    private String creater;
    //逻辑撤销标记（0：未撤销；1：已撤销）
    private Integer revokeFlag;
    //通知被撤销的时间
    private Date revokeTime;
    //逻辑删除标记（0：未删除；1：已删除）
    private Integer delFlag;
    //通知被删除的时间
    private Date delTime;

}
