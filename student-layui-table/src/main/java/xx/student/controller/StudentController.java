package xx.student.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.web.servlet.ModelAndView;
import xx.student.entity.Student;
import xx.student.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Student)表控制层
 *
 * @author halo-king
 * @since 2023-07-07 15:34:49
 */
@RestController
@RequestMapping("student")
public class StudentController {
    /**
     * 服务对象
     */
    @Resource
    private StudentService studentService;


    @GetMapping("Index")
    public ModelAndView toPage() {
        return new ModelAndView("Student");
    }

    /**
     * 分页查询学生信息
     *
     * @param current
     * @param size
     * @param student
     * @return
     */
    @GetMapping("queryStudentByPage")
    public PageInfo<Student> queryStudentByPage(int current, int size, Student student) {
        return this.studentService.queryAllByLimit(current, size, student);
    }

    /**
     * 新增学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("saveStudent")
    public Integer saveStudent(@RequestBody Student student) {
        return this.studentService.insert(student);
    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("updateStudent")
    public Integer updateStudent(@RequestBody Student student) {
        return this.studentService.update(student);
    }

    /**
     * 删除学生信息
     *
     * @param id
     * @return
     */
    @PostMapping("deleteStudent")
    public Boolean deleteStudent(Integer id) {
        return this.studentService.deleteById(id);
    }

}
