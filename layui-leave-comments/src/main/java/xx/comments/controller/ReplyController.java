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
import java.util.Date;
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
     * 新增数据
     *
     * @param reply 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    public R insert(@RequestBody Reply reply) {
        reply.setCreateTime(new Date());
        return success(this.replyService.save(reply));
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
