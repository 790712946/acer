package com.acfun.listener;

import com.acfun.AcerApplication;
import com.acfun.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jianghong on 2017/3/19.
 * 任务监听器，继承JobExecutionListenerSupport类
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 任务结束后，可以检查任务执行结果
     *
     * @param jobExecution
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            //从数据库中查询数据（JDBC方式）
            List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", (rs, row) ->
                    new Person(rs.getString(1), rs.getString(2)));
            //遍历查询结果
            results.forEach(person -> log.info("Found <" + person + "> in the database."));
        }
    }

    //任务开始前，可以做任务预处理
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("JOB Start.");
    }
}
