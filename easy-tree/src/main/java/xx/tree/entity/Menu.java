package xx.tree.entity;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private Long id;

    private Long parentId;

    private String name;

    private List<Menu> childrenList;

    public Menu(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
