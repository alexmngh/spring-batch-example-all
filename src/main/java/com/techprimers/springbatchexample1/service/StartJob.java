package com.techprimers.springbatchexample1.service;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StartJob {

  @Autowired
  JobLauncher jobLauncherList;

  @Autowired
  Job jobDeveloperMany;


  @Scheduled(fixedRate = 15000, initialDelay = 20000)
  public void launchJob() throws Exception {
    log.info("launchJob");

    /*
    Map<String, JobParameter> maps = new HashMap<>();
    maps.put("time", new JobParameter(System.currentTimeMillis()));
    JobParameters parameters = new JobParameters(maps);
    JobExecution jobExecution = jobLauncherList.run(jobListObjectBath, parameters);

    System.out.println("JobExecution List: " + jobExecution.getStatus());

    System.out.println("JobExecution: " + jobExecution
        .getJobParameters().getParameters().toString());

    System.out.println("Batch is Running...");
    while (jobExecution.isRunning()) {
      System.out.println("...");
    }
    System.out.println("end load");

     */

    JobParameters params = new JobParametersBuilder()
        .addString("JobID", String.valueOf(System.currentTimeMillis()))
        .toJobParameters();
    jobLauncherList.run(jobDeveloperMany, params);
  }

}
