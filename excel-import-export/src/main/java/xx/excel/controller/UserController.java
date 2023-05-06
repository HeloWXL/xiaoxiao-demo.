package xx.excel.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xx.excel.entity.User;
import xx.excel.service.UserService;
import xx.excel.util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author halo-king
 * @since 2023-05-05 17:20:43
 */
@RestController
@RequestMapping("user")
public class UserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @GetMapping("toPage")
    public ModelAndView toPage() {
        return new ModelAndView("User");
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping("selectAll")
    public R selectAll(Page<User> page, User user) {
        return success(this.userService.page(page, new QueryWrapper<>(user)));
    }

    /**
     * 导出
     * @param response
     */
    @GetMapping("/export")
    public void exportUserExcel(HttpServletResponse response) {
        try {
            this.setExcelResponseProp(response, "用户列表");
            List<User> userList = this.userService.list();
            EasyExcel.write(response.getOutputStream())
                    .head(User.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("用户列表")
                    .doWrite(userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public Result importUserExcel(@RequestPart(value = "file") MultipartFile file) {
        try {
            List<User> userList = EasyExcel.read(file.getInputStream())
                    .head(User.class)
                    .sheet()
                    .doReadSync();
            // 批量插入
            boolean res = userService.saveBatch(userList);
            return Result.success("success",res);
        } catch (IOException e) {
            return Result.fail("导入失败");
        }
    }


    /**
     * 设置响应结果
     *
     * @param response    响应结果对象
     * @param rawFileName 文件名
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    private void setExcelResponseProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }
}
