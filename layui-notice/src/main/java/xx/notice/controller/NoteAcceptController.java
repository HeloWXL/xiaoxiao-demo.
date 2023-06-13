package xx.notice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.notice.entity.NoteAccept;
import xx.notice.service.NoteAcceptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 每条通知对应接收人的接受情况(NoteAccept)表控制层
 *
 * @author halo-king
 * @since 2023-06-13 14:17:31
 */
@RestController
@RequestMapping("noteAccept")
public class NoteAcceptController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private NoteAcceptService noteAcceptService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param noteAccept 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<NoteAccept> page, NoteAccept noteAccept) {
        return success(this.noteAcceptService.page(page, new QueryWrapper<>(noteAccept)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.noteAcceptService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param noteAccept 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody NoteAccept noteAccept) {
        return success(this.noteAcceptService.save(noteAccept));
    }

    /**
     * 修改数据
     *
     * @param noteAccept 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody NoteAccept noteAccept) {
        return success(this.noteAcceptService.updateById(noteAccept));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.noteAcceptService.removeByIds(idList));
    }
}
