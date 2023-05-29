package xx.upload.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.upload.entity.UploadFile;
import xx.upload.service.UploadFileService;
import org.springframework.web.bind.annotation.*;
import xx.upload.util.Result;

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
