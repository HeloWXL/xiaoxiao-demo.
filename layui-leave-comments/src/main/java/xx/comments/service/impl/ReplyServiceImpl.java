package xx.comments.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.comments.dao.ReplyDao;
import xx.comments.entity.Reply;
import xx.comments.service.ReplyService;
import org.springframework.stereotype.Service;

/**
 * (Reply)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-10 15:13:57
 */
@Service("replyService")
public class ReplyServiceImpl extends ServiceImpl<ReplyDao, Reply> implements ReplyService {

}
