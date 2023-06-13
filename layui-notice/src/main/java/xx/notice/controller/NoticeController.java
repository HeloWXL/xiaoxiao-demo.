package xx.notice.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xx.notice.entity.NoteSource;
import xx.notice.entity.User;
import xx.notice.filter.SessionContent;
import xx.notice.service.NoteSourceService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("notice")
public class NoticeController extends ApiController {

    @Resource
    NoteSourceService noteSourceService;

    /**
     * 发布通知
     * @return
     */
    @PostMapping("publish")
    public R publish(@RequestBody NoteSource noteSource){
        Map map = new HashMap<String, Object>(2);
        String noteContent = noteSource.getNoteContent();
        // 校验新通知内容
        if (StringUtils.isEmpty(noteContent)) {
            map.put("code", -1);
            map.put("msg", "发布失败，新通知的内容不能为空！");
            return success(map);
        }
        // 检验通知内容是否超长
        if (noteContent.length() > 500) {
            map.put("code", -1);
            map.put("msg", "发布失败，新通知的内容不能超过500字符！");
            return success(map);
        }
        // 避免 XSS 漏洞
        noteContent = noteContent.toLowerCase();
        noteContent = noteContent.replaceAll("<.?script>", "&lt;script&gt;");
        noteContent = noteContent.replaceAll("<.?iframe>", "&lt;iframe&gt;");
        noteSource.setNoteContent(noteContent);
        User user  = SessionContent.getUserInfoLocal();
        noteSource.setCreater(user.getUserId());
        return R.ok(noteSourceService.publishNotice(noteSource));
    }

}
