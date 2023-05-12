package xx.upload.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.upload.entity.UploadFile;
import xx.upload.service.UploadFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UploadFile)表控制层
 *
 * @author halo-king
 * @since 2023-05-11 14:29:58
 */
@RestController
@RequestMapping("uploadFile")
public class UploadFileController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UploadFileService uploadFileService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param uploadFile 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<UploadFile> page, UploadFile uploadFile) {
        return success(this.uploadFileService.page(page, new QueryWrapper<>(uploadFile)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.uploadFileService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param uploadFile 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody UploadFile uploadFile) {
        return success(this.uploadFileService.save(uploadFile));
    }

    /**
     * 修改数据
     *
     * @param uploadFile 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody UploadFile uploadFile) {
        return success(this.uploadFileService.updateById(uploadFile));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.uploadFileService.removeByIds(idList));
    }
}
