package xx.comments.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xx.comments.entity.Reply;

/**
 * (Reply)表数据库访问层
 *
 * @author halo-king
 * @since 2023-05-10 15:13:57
 */
@Mapper
public interface ReplyDao extends BaseMapper<Reply> {

}
