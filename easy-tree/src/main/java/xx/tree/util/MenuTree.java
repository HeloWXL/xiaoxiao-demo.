package xx.tree.util;

import xx.tree.entity.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuTree {

    private final List<Menu> menuList;

    public MenuTree(List<Menu> menuList) {
        this.menuList = menuList;
    }

    /**
     * 获取根结点
     *
     * @return
     */
    private List<Menu> getRootNode() {
        List<Menu> rootNode = new ArrayList<>();
        menuList.forEach(item -> {
            if (item.getParentId() == 0) {
                rootNode.add(item);
            }
        });
        return rootNode;
    }

    /**
     * 构建子树
     *
     * @param rootNode
     * @return
     */
    private Menu builderChildrenNode(Menu rootNode) {
        List<Menu> childrenList = new ArrayList<>();
        menuList.forEach(item -> {
            if (Objects.equals(item.getParentId(), rootNode.getId())) {
                // 还需要遍历三级菜单以后的
                Menu menu = builderChildrenNode(item);
                childrenList.add(menu);
            }
        });

        rootNode.setChildrenList(childrenList);
        return rootNode;
    }

    /**
     * 构建树
     *
     * @return
     */
    public List<Menu> buildTree() {
        List<Menu> menus = getRootNode();
        menus.forEach(this::builderChildrenNode);
        return menus;
    }


    public static void main(String[] args) {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(1L, 0L, "一级菜单1"));
        menuList.add(new Menu(2L, 0L, "一级菜单2"));
        menuList.add(new Menu(3L, 0L, "一级菜单3"));
        menuList.add(new Menu(4L, 1L, "二级菜单1"));
        menuList.add(new Menu(5L, 1L, "二级菜单2"));
        menuList.add(new Menu(6L, 4L, "三级菜单"));
        menuList.add(new Menu(7L, 6L, "四级菜单"));

        MenuTree menuTree = new MenuTree(menuList);
        List<Menu> menus = menuTree.buildTree();
    }
}
