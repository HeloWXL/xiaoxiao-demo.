package xx.tree.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xx.tree.entity.Menu;

import java.util.List;

/**
 * (Menu)表服务接口
 *
 * @author wang.xianlin
 * @since 2023-06-10 12:20:00
 */
public interface MenuService extends IService<Menu> {
    List<Menu> queryMenuTreeBySQL();
}

