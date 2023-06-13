package xx.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.notice.dao.NoteSourceDao;
import xx.notice.entity.NoteSource;
import xx.notice.service.NoteSourceService;
import org.springframework.stereotype.Service;

/**
 * 与“通知”本身相关的具体信息，包括通知的内容、通知的类型和通知的对象类型等(NoteSource)表服务实现类
 *
 * @author halo-king
 * @since 2023-06-13 14:17:19
 */
@Service("noteSourceService")
public class NoteSourceServiceImpl extends ServiceImpl<NoteSourceDao, NoteSource> implements NoteSourceService {

}
