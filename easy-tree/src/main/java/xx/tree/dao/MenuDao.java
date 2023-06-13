package xx.tree.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xx.tree.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Menu)表数据库访问层
 *
 * @author wang.xianlin
 * @since 2023-06-10 12:19:43
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {
    /**
     * 通过SQL查询菜单树
     * @return
     */
    List<Menu> queryMenuTreeBySQL();
}

