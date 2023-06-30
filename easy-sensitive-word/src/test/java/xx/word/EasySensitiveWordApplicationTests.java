package xx.word;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xx.word.filter.SensitiveFilter;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= EasySensitiveWordApplication.class)
public class EasySensitiveWordApplicationTests {

	@Resource
	SensitiveFilter sensitiveFilter;

	@Test
	public void test() {
		String str = "我们周末一起去嫖娼，然后，完事之后 再去澳门赌场赌博，顺便吸毒 ";
		System.out.println(sensitiveFilter.filter(str));
	}

}
