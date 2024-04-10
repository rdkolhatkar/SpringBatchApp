package com.ratnakar.project.SpringBatchApp.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//@Service
public class SecondJobScheduler {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void secondJobScheduler(){

        Map<String, JobParameter> parameterMap = new HashMap<>();

        parameterMap.put("currentTime", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(parameterMap);

        try {
            JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
            System.out.println("Job Execution Id = " + jobExecution.getId());
        }catch (Exception e){
            System.out.println("Exception while starting Job");
        }

    }
}
