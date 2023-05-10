package xx.comments.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xx.comments.entity.Leave;

/**
 * (Leave)表数据库访问层
 *
 * @author halo-king
 * @since 2023-05-10 15:12:19
 */
@Mapper
public interface LeaveDao extends BaseMapper<Leave> {

}
