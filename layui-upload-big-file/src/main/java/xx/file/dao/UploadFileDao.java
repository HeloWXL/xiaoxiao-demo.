package xx.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xx.file.entity.UploadFile;

/**
 * (UploadFile)表数据库访问层
 *
 * @author halo-king
 * @since 2023-05-11 14:29:57
 */
@Mapper
public interface UploadFileDao extends BaseMapper<UploadFile> {

}
