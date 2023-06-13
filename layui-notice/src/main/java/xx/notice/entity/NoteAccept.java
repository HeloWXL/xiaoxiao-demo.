package xx.notice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 每条通知对应接收人的接受情况(NoteAccept)表实体类
 *
 * @author halo-king
 * @since 2023-06-13 14:17:30
 */
@SuppressWarnings("serial")
@Data
public class NoteAccept extends Model<NoteAccept> {
    //通知主键（对应通知详情表中的一条具体记录）
    @TableId(type = IdType.AUTO)
    private Long noteId;
    //员工主键（对应员工信息表中的一条具体记录）
    private String staffId;
    //通知的类型（0：实时通知；1：登录时通知；2：定时通知）
    private Integer noteType;
    //通知的结果（0：未通知到；1：已通知到）
    private Integer noteRes;
    //员工接收通知的结果（0：未读，可能员工不在线；1：已读）
    private Integer acceptRes;
    //员工接收到通知的时间
    private Date acceptTime;
    //当前记录的创建时间
    private Date createTime;
}
