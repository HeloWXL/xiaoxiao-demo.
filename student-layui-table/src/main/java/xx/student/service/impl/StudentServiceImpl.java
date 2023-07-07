package xx.student.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import xx.student.entity.Student;
import xx.student.dao.StudentDao;
import xx.student.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Student)表服务实现类
 *
 * @author halo-king
 * @since 2023-07-07 15:34:48
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Student queryById(Integer id) {
        return this.studentDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public PageInfo<Student> queryAllByLimit(int offset, int limit,Student student) {
        //获取第pageNum页，pageSize条内容，默认查询总数count
        PageHelper.startPage(offset, limit);
        // 紧跟着的第一个select方法会被分页
        List<Student> list = this.studentDao.queryAll(student);
        //用PageInfo对结果进行包装
        return new PageInfo<>(list);
    }

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(Student student) {
        return this.studentDao.insert(student);
    }

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(Student student) {
        return this.studentDao.update(student);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.studentDao.deleteById(id) > 0;
    }
}
