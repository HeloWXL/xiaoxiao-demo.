package xx.comments.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.comments.dao.LeaveDao;
import xx.comments.entity.Leave;
import xx.comments.service.LeaveService;
import org.springframework.stereotype.Service;

/**
 * (Leave)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-10 15:12:20
 */
@Service("leaveService")
public class LeaveServiceImpl extends ServiceImpl<LeaveDao, Leave> implements LeaveService {

}
