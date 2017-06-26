package com.acfun;

import cn.sunline.ltts.core.api.util.LttsCoreBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AcerApplicationTests {
	@Test
	public void contextLoads() throws NoSuchFieldException, IllegalAccessException {
		Class integer = Integer.class.getDeclaredClasses()[0];
		Field field = integer.getDeclaredField("cache");
		field.setAccessible(true);
		Integer[] array =  (Integer[]) field.get(integer);
		array[130] = array[131];
		System.out.printf("%d", 1 + 1);
	}

}
