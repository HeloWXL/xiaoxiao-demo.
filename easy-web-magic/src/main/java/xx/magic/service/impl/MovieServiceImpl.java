package xx.magic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xx.magic.dao.MovieDao;
import xx.magic.entity.Movie;
import xx.magic.service.MovieService;
import org.springframework.stereotype.Service;

/**
 * (Movie)表服务实现类
 *
 * @author wang.xianlin
 * @since 2023-06-17 18:09:32
 */
@Service("movieService")
public class MovieServiceImpl extends ServiceImpl<MovieDao, Movie> implements MovieService {

}

