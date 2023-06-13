package xx.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.notice.dao.NoteAcceptDao;
import xx.notice.dao.NoteSourceDao;
import xx.notice.dao.UserDao;
import xx.notice.entity.NoteAccept;
import xx.notice.entity.NoteSource;
import org.springframework.stereotype.Service;
import xx.notice.service.NoteSourceService;
import javax.annotation.Resource;
import java.util.*;

/**
 * 与“通知”本身相关的具体信息，包括通知的内容、通知的类型和通知的对象类型等(NoteSource)表服务实现类
 *
 * @author halo-king
 * @since 2023-06-13 14:17:19
 */
@Service("noteSourceService")
public class NoteSourceServiceImpl extends ServiceImpl<NoteSourceDao, NoteSource> implements NoteSourceService {

    @Resource
    UserDao userDao;

    @Resource
    NoteAcceptDao noteAcceptDao;

    /**
     * 发布新通知
     *
     * @param notice
     */
    @Override
    public Map<String, Object> publishNotice(NoteSource notice) {
        int i = handleNoticeByType(notice);
        return handleResult(i);
    }

    /**
     * 根据通知类型，进行不同处理
     * - 0：即时通知；1：登录时通知；2：定时通知
     *
     * @param notice
     */
    private int handleNoticeByType(NoteSource notice) {
        // 默认参数有误
        int code = 0;
        // 获取通知类型
        Integer noteType = notice.getNoteType();
        // 检验参数
        if (noteType == null) {
            return code;
        }
        // 一切正常，封装通知接收的信息
        NoteAccept acceptDto;
        // 判断通知的类型
        if ("1".equals(noteType)) {
            // 如果是“即时通知”,继续按“接收人类型”处理
            notice.setRegularTime(null);
            HandleDto handleDto = handleNoticeByAcceptType(notice);
            if (handleDto.code < 1) {
                return handleDto.code;
            }
            // 如果一切处理正常，封装通知接收信息
            acceptDto = handleDto.acceptDto;
            acceptDto.setNoteType(noteType);
            // 遍历通知的接受对象，并将通知的接受信息保存到数据库
            for (String user : handleDto.users) {
                // 将通知接收信息保存到数据库
                acceptDto.setStaffId(user);
                // 标记为“已通知”
                acceptDto.setNoteRes(1);
                noteAcceptDao.insert(acceptDto);
                // 赋值通知的主键
                notice.setNoteId(acceptDto.getNoteId());
                // 立即通知在线用户
                //ChatNoticeWebsocket.sendNotice(notice, user);
            }
            // 标记通知发布成功
            code = 1;
        }
        // 如果是“登录时通知”,继续按“接收人类型”处理
        else if ("2".equals(noteType)) {

            notice.setRegularTime(null);
            HandleDto handleDto = handleNoticeByAcceptType(notice);
            if (handleDto.code < 1) {
                return handleDto.code;
            }
            // 封装通知接收信息
            handleNoticeAcceptInfo(handleDto, noteType);
            // 标记通知发布成功
            code = 1;
        }
        // 如果是“定时通知”，需要先检验“定时发布的时间”参数
        else if ("3".equals(noteType)) {
            final Date regularTime = notice.getRegularTime();
            long currentTime = System.currentTimeMillis();
            if (regularTime == null || regularTime.getTime() <= currentTime) {
                // 定时发布时间为空或小于当前时间，参数无效
                return -1;
            }
            // 继续按“接收人类型”处理
            HandleDto handleDto = handleNoticeByAcceptType(notice);
            if (handleDto.code < 1) {
                return handleDto.code;
            }
            // 封装通知接收信息
            handleNoticeAcceptInfo(handleDto, noteType);
            // 创建定时任务：发送定时通知
            for (String user : handleDto.users) {
                //ChatRegularNoticeService.scheduleRegularNote(notice, user);
            }
            // 标记通知发布成功
            code = 1;
        }
        return code;
    }

    /**
     * 保存通知接收信息到数据库中
     *
     * @param handleDto
     * @param noteType
     */
    private void handleNoticeAcceptInfo(HandleDto handleDto, int noteType) {
        // 如果一切处理正常，封装通知接收信息
        NoteAccept acceptDto = handleDto.acceptDto;
        acceptDto.setNoteType(noteType);
        // 标记“未通知”
        acceptDto.setNoteRes(0);
        // 遍历通知的接受对象，并将通知的接受信息保存到数据库
        for (String user : handleDto.users) {
            // 将通知接收信息保存到数据库
            acceptDto.setStaffId(user);
            noteAcceptDao.insert(acceptDto);
        }
    }

    /**
     * 按通知接收人的类型，对通知进行不同处理
     */
    private HandleDto handleNoticeByAcceptType(NoteSource notice){
        // 返回的结果
        HandleDto handleDto = new HandleDto();
        // 获取接收人类型
        Integer acceptType = notice.getAcceptType();
        // 检验参数
        if (acceptType == null) {
            return handleDto;
        }
        // 通知的受众
        List<String> users = null;
        String acceptObj = notice.getAcceptObj();
        // 所有人
        if ("1".equals(acceptType)) {
            notice.setAcceptObj("all");
            // 如果通知“全部员工”，获取全部员工
            users = userDao.queryUserList();
        }
        // 指定个人
         else if ("2".equals(acceptType)) {
            users = new ArrayList<String>(1);
            users.add(acceptObj);
        } else {
            return handleDto;
        }

        // 检验接收对象
        if (users == null || users.size() == 0) {
            return handleDto;
        }

        // 保存“通知”到数据库
        Integer noteId = this.baseMapper.insert(notice);
        // 一切正常，封装通知接收的信息
        NoteAccept acceptDto = new NoteAccept();
        acceptDto.setNoteId(noteId);
        // 返回通知的员工
        return new HandleDto(1, users, acceptDto);
    }

    /**
     * 通知发布处理的统一反馈结果
     * - code > 0 时表示一切正常，否则，表示存在对应错误
     *
     * @param code 错误码
     */
    private Map<String, Object> handleResult(int code) {
        Map map = new HashMap<String, Object>(2);
        map.put("code", code);
        String msg = null;
        switch (code) {
            case 0:
                msg = "发布失败，部分入参无效！";
                break;
            case -1:
                msg = "发布失败，定时发送的时间参数有误！";
                break;
            case -500:
                msg = "发布失败，系统故障，请稍后再试！";
            default:
        }
        map.put("msg", msg);
        return map;
    }

    /**
     * 用于数据传输的内部类
     */
    private class HandleDto {
        int code;
        List<String> users;
        NoteAccept acceptDto;

        HandleDto() {
            this.code = 0;
        }

        HandleDto(int code) {
            this.code = code;
        }

        HandleDto(int code, List<String> users, NoteAccept acceptDto) {
            this.code = code;
            this.users = users;
            this.acceptDto = acceptDto;
        }
    }
}
