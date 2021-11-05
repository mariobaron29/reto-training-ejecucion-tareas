package com.sofka.ejecuciontareas.tasks.listener;

import com.sofka.ejecuciontareas.JobController;
import com.sofka.ejecuciontareas.common.event.JobExecutedEvent;
import com.sofka.ejecuciontareas.common.event.JobScheduledEvent;
import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.Date;
import java.util.UUID;

public class JobRuntimeListener implements JobListener, JobFactory {
    private final JobController executionController;
    private final  JobEvent jobEvent;

    public JobRuntimeListener(JobController executionController, JobEvent jobEvent) {
        this.executionController = executionController;
        this.jobEvent = jobEvent;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("Job with key " + context.getJobDetail().getKey().toString()
                + " finished in " + context.getJobRunTime() + " ms.");

        System.out.println("JobDetail: "+context.getJobDetail().toString());

        executionController.processJobExecuted(JobExecution.builder()
                        .executionTime(context.getJobRunTime())
                        .scheduledTime(context.getScheduledFireTime())
                        .startTime(context.getFireTime())
                        .id(UUID.randomUUID().toString())
                        .jobId(context.getJobDetail().getKey().getName())
                        .endTime(new Date())
                        .httpCode("200")//TODO: como lo obtengo?
                        .output("") // TODO: que es?
                .build(), JobEvent.builder()
                        .url(jobEvent.getUrl())
                        .timeZone(jobEvent.getTimeZone())
                        .status(jobEvent.getStatus())
                        .email(jobEvent.getEmail())
                        .cronRegExp(jobEvent.getCronRegExp())
                        .eventName(JobExecutedEvent.EVENT_NAME)
                        .eventId(UUID.randomUUID().toString())
                        .jobId(jobEvent.getJobId())
                    .build());
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job to be executed with key " + context.getJobDetail().getKey().toString()
                + " finished in " + context.getJobRunTime() + " ms.");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job vetoed with key " + context.getJobDetail().getKey().toString()
                + " finished in " + context.getJobRunTime() + " ms.");
    }
}