package xx.comments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.comments.entity.Reply;
import xx.comments.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Reply)表控制层
 *
 * @author halo-king
 * @since 2023-05-10 15:13:58
 */
@RestController
@RequestMapping("reply")
public class ReplyController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ReplyService replyService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param reply 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Reply> page, Reply reply) {
        return success(this.replyService.page(page, new QueryWrapper<>(reply)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.replyService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param reply 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Reply reply) {
        return success(this.replyService.save(reply));
    }

    /**
     * 修改数据
     *
     * @param reply 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Reply reply) {
        return success(this.replyService.updateById(reply));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.replyService.removeByIds(idList));
    }
}
