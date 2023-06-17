package xx.magic.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * (Movie)表实体类
 *
 * @author wang.xianlin
 * @since 2023-06-17 18:09:31
 */
@Data
@TableName("movie")
@SuppressWarnings("serial")
public class Movie extends Model<Movie> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String movieName;

    private String intro;

    private String director;

    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}

