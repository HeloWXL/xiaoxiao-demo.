package xx.notice.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xx.notice.entity.NoteSource;

/**
 * 与“通知”本身相关的具体信息，包括通知的内容、通知的类型和通知的对象类型等(NoteSource)表数据库访问层
 *
 * @author halo-king
 * @since 2023-06-13 14:17:18
 */
@Mapper
public interface NoteSourceDao extends BaseMapper<NoteSource> {

}
