package xx.magic.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xx.magic.entity.Movie;
import xx.magic.service.MovieService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Movie)表控制层
 *
 * @author wang.xianlin
 * @since 2023-06-17 18:09:30
 */
@RestController
@RequestMapping("movie")
public class MovieController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MovieService movieService;

    /**
     * 所有数据
     *
     * @return 所有数据
     */
    @GetMapping("query")
    public R selectAll() {
        return success(this.movieService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.movieService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param movie 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Movie movie) {
        return success(this.movieService.save(movie));
    }

    /**
     * 修改数据
     *
     * @param movie 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Movie movie) {
        return success(this.movieService.updateById(movie));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.movieService.removeByIds(idList));
    }
}

