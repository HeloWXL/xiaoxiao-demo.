package xx.upload.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (UploadFile)表实体类
 *
 * @author halo-king
 * @since 2023-05-11 14:29:55
 */
@SuppressWarnings("serial")
public class UploadFile extends Model<UploadFile> {
    //主键ID
    private Integer id;
    //文件名
    private String fileName;
    //文件大小
    private Integer fileSize;
    //路径
    private String filePath;
    //上传时间
    private Date createTime;
}
