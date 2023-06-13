package xx.tree.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.tree.dao.MenuDao;
import xx.tree.entity.Menu;
import xx.tree.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Menu)表服务实现类
 *
 * @author wang.xianlin
 * @since 2023-06-10 12:20:01
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Override
    public List<Menu> queryMenuTreeBySQL() {
        return this.baseMapper.queryMenuTreeBySQL();
    }
}

