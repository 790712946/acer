package com.acfun.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by jianghong on 2017/3/24.
 */
@Service
public class JobService implements TestInterface<String>{
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job importUserJob;
    //每隔五秒执行一次
//    @Scheduled(fixedRate=5000)
    public void executeJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        //以时间戳标识任务
        JobParameters jobParameter=new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution=jobLauncher.run(importUserJob,jobParameter);
    }

    @Override
    public void test(String s) {

    }
}
