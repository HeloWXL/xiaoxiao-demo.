package xx.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xx.file.dao.UploadFileDao;
import xx.file.entity.UploadFile;
import xx.file.service.UploadFileService;

/**
 * (UploadFile)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-11 14:29:58
 */
@Service("uploadFileService")
public class UploadFileServiceImpl extends ServiceImpl<UploadFileDao, UploadFile> implements UploadFileService {

}
