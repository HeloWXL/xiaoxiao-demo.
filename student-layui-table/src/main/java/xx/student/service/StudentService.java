package xx.student.service;

import com.github.pagehelper.PageInfo;
import xx.student.entity.Student;

import java.util.List;

/**
 * (Student)表服务接口
 *
 * @author halo-king
 * @since 2023-07-07 15:34:47
 */
public interface StudentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Student queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    PageInfo<Student> queryAllByLimit(int offset, int limit,Student student);

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    Integer insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    Integer update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
