package xx.tree.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * (Menu)表实体类
 *
 * @author wang.xianlin
 * @since 2023-06-10 12:19:43
 */
@Data
@SuppressWarnings("serial")
public class Menu extends Model<Menu> {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private String menuName;

    private String menuIcon;
    
    @TableField(exist = false)
    private List<Menu> childrenList;

}

