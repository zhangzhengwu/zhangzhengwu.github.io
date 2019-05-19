package base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:configure.properties"
		,"classpath:log4j.properties"
		,"classpath:proxool.properties"
		
	 })
public class BaseJunit4Test {
 

}
