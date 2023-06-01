package xx.file.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xx.file.entity.UploadFile;
import xx.file.service.UploadFileService;
import xx.file.util.Result;

import javax.annotation.Resource;

/**
 * (UploadFile)表控制层
 *
 * @author halo-king
 * @since 2023-05-11 14:29:58
 */
@RestController
@RequestMapping("uploadFile")
public class UploadFileController {
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
    @GetMapping("selectAll")
    public Result selectAll(Page<UploadFile> page, UploadFile uploadFile) {
        return Result.success(this.uploadFileService.page(page, new QueryWrapper<>(uploadFile)));
    }
}
