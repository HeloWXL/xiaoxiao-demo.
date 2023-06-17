package xx.magic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xx.magic.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Movie)表数据库访问层
 *
 * @author wang.xianlin
 * @since 2023-06-17 18:09:30
 */
@Mapper
public interface MovieDao extends BaseMapper<Movie> {

}

