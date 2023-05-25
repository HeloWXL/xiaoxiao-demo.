package xx.notice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xx.login.entity.User;

/**
 * (User)表数据库访问层
 *
 * @author halo-king
 * @since 2023-05-05 17:20:41
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
