package xx.comments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.comments.entity.Leave;
import xx.comments.entity.Reply;
import xx.comments.service.LeaveService;
import org.springframework.web.bind.annotation.*;
import xx.comments.service.ReplyService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
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

    @Resource
    private ReplyService replyService;

    /**
     * 分页查询所有数据
     * @return 所有数据
     */
    @GetMapping("queryAll")
    public R queryAll() {
        List<Leave> list = this.leaveService.list();
        list.stream().forEach(item->{
            item.setReplyList(this.replyService.list(new QueryWrapper<Reply>().eq("leave_id",item.getLeaveId())));
        });
        return success(list);
    }

    /**
     * 新增数据
     *
     * @param leave 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    public R insert(@RequestBody Leave leave) {
        leave.setCreateTime(new Date());
        return success(this.leaveService.save(leave));
    }

    /**
     * 修改数据
     *
     * @param leave 实体对象
     * @return 修改结果
     */
    @PostMapping("update")
    public R update(@RequestBody Leave leave) {
        return success(this.leaveService.updateById(leave));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping("delete")
    public R delete(@RequestBody List<Long> idList) {
        return success(this.leaveService.removeByIds(idList));
    }
}
