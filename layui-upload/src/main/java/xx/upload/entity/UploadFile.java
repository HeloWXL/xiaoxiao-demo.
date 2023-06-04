package xx.upload.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
/**
 * (UploadFile)表实体类
 *
 * @author halo-king
 * @since 2023-05-11 14:29:55
 */
@SuppressWarnings("serial")
@Data
public class UploadFile extends Model<UploadFile> {
    //主键ID
    @TableId(type = IdType.UUID)
    private String id;
    //文件名
    private String fileName;
    //文件大小
    private Long fileSize;
    //路径
    private String filePath;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


    public UploadFile() {
    }

    public UploadFile(String fileName, Long fileSize, String filePath, Date createTime) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.createTime = createTime;
    }
}
