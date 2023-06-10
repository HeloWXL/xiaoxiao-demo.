package xx.tree.controller;



import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.servlet.ModelAndView;
import xx.tree.entity.Menu;
import xx.tree.service.MenuService;
import org.springframework.web.bind.annotation.*;
import xx.tree.util.MenuTree;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Menu)表控制层
 *
 * @author wang.xianlin
 * @since 2023-06-10 12:19:42
 */
@RestController
@RequestMapping("menu")
public class MenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("queryMenuTree")
    public R queryMenuTree() {
        List<Menu> menuList = this.menuService.list();
        MenuTree menuTree = new MenuTree(menuList);
        List<Menu> menus = menuTree.buildTree();
        return R.ok(menus);
    }



    @GetMapping("index")
    public ModelAndView toMenu() {
        List<Menu> menuList = this.menuService.list();
        MenuTree menuTree = new MenuTree(menuList);
        List<Menu> menus = menuTree.buildTree();
        return new ModelAndView("Menu").addObject("menus",menus);
    }

}

