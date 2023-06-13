package xx.notice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.notice.entity.NoteSource;
import xx.notice.service.NoteSourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 与“通知”本身相关的具体信息，包括通知的内容、通知的类型和通知的对象类型等(NoteSource)表控制层
 *
 * @author halo-king
 * @since 2023-06-13 14:17:19
 */
@RestController
@RequestMapping("noteSource")
public class NoteSourceController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private NoteSourceService noteSourceService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param noteSource 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<NoteSource> page, NoteSource noteSource) {
        return success(this.noteSourceService.page(page, new QueryWrapper<>(noteSource)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.noteSourceService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param noteSource 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody NoteSource noteSource) {
        return success(this.noteSourceService.save(noteSource));
    }

    /**
     * 修改数据
     *
     * @param noteSource 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody NoteSource noteSource) {
        return success(this.noteSourceService.updateById(noteSource));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.noteSourceService.removeByIds(idList));
    }
}
