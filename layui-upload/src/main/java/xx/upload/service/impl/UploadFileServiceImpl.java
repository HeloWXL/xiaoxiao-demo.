package xx.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.upload.dao.UploadFileDao;
import xx.upload.entity.UploadFile;
import xx.upload.service.UploadFileService;
import org.springframework.stereotype.Service;

/**
 * (UploadFile)表服务实现类
 *
 * @author halo-king
 * @since 2023-05-11 14:29:58
 */
@Service("uploadFileService")
public class UploadFileServiceImpl extends ServiceImpl<UploadFileDao, UploadFile> implements UploadFileService {

}
