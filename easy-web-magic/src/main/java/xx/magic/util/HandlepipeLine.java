package xx.magic.util;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import xx.magic.entity.Movie;
import xx.magic.service.MovieService;

import javax.annotation.Resource;

@Component
public class HandlepipeLine implements Pipeline {

    @Resource
    MovieService movieService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Movie movie = resultItems.get("movieInfo");
        if (movie != null) {
            movieService.save(movie);
        }
    }
}
