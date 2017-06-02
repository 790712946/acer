package com.acfun;

import com.acfun.dao.PersonMapper;
import com.acfun.domain.Person;
import com.acfun.service.JobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AcerApplicationTests {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JobService jobService;
	@Autowired
	private PersonMapper personMapper;
	@Test
	public void contextLoads() throws SQLException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//		System.out.println(dataSource.getConnection().toString());
//		Connection connection=dataSource.getConnection();
//        System.out.println(connection.getClientInfo());
//		jobService.executeJob();
		Person person=personMapper.selectOneByEnity(new Person("test","test"));
		System.out.println(person);
		Map<String,String> map=new HashMap<>();
		map.put("firstName","test");
		map.put("lastName","test");
		System.out.println(personMapper.selectOneByMap(map));
    }

}
