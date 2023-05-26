package xx.comments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.comments.entity.Leave;
import xx.comments.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Leave)表控制层
 *
 * @author halo-king
 * @since 2023-05-10 15:12:21
 */
@RestController
@RequestMapping("leave")
public class LeaveController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private LeaveService leaveService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param leave 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Leave> page, Leave leave) {
        return success(this.leaveService.page(page, new QueryWrapper<>(leave)));
    }

    /**
     * 新增数据
     *
     * @param leave 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Leave leave) {
        return success(this.leaveService.save(leave));
    }

    /**
     * 修改数据
     *
     * @param leave 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Leave leave) {
        return success(this.leaveService.updateById(leave));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.leaveService.removeByIds(idList));
    }
}
