package xx.wallpaper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xx.wallpaper.util.HandleProcessor;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= EasyWallpaperSpiderApplication.class)
public class EasyWallpaperSpiderApplicationTests {

	@Resource
	HandleProcessor  handleProcessor;

	@Test
	public void contextLoads() {
		handleProcessor.start();
	}

}
