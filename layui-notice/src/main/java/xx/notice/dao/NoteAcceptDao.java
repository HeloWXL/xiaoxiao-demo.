package xx.notice.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xx.notice.entity.NoteAccept;

/**
 * 每条通知对应接收人的接受情况(NoteAccept)表数据库访问层
 *
 * @author halo-king
 * @since 2023-06-13 14:17:31
 */
@Mapper
public interface NoteAcceptDao extends BaseMapper<NoteAccept> {

}
