package xx.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.notice.dao.NoteAcceptDao;
import xx.notice.entity.NoteAccept;
import xx.notice.service.NoteAcceptService;
import org.springframework.stereotype.Service;

/**
 * 每条通知对应接收人的接受情况(NoteAccept)表服务实现类
 *
 * @author halo-king
 * @since 2023-06-13 14:17:31
 */
@Service("noteAcceptService")
public class NoteAcceptServiceImpl extends ServiceImpl<NoteAcceptDao, NoteAccept> implements NoteAcceptService {

}
