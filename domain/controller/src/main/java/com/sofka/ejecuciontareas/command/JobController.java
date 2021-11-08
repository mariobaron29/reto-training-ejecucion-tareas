package com.sofka.ejecuciontareas.command;

import com.sofka.ejecuciontareas.common.event.JobExecutedEvent;
import com.sofka.ejecuciontareas.common.event.JobScheduledEvent;
import com.sofka.ejecuciontareas.common.event.notification.CanonicalNotification;
import com.sofka.ejecuciontareas.common.event.notification.HeaderFactory;
import com.sofka.ejecuciontareas.configbuilder.ConfigBuilder;
import com.sofka.ejecuciontareas.domain.canonical.event.JobEventCanonical;
import com.sofka.ejecuciontareas.domain.canonical.jobexecution.JobExecutionCanonical;
import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobFactory;
import com.sofka.ejecuciontareas.domain.response.JobResponse;
import com.sofka.ejecuciontareas.command.tasks.listener.JobRuntimeListener;
import com.sofka.ejecuciontareas.command.tasks.Task;
import com.sofka.ejecuciontareas.command.tasks.CustomJobFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Log
@RequiredArgsConstructor
public class JobController implements JobFactory, HeaderFactory {

    private final ConfigBuilder configBuilder;
    private final String uuid = UUID.randomUUID().toString();
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    Scheduler scheduler;

    public Mono<Void> processCreateJobEvent(JobEvent event) {
        return scheduleNewJob(event)
                .then(emitJobScheduledEvent(buildJobEventCanonical(event), uuid, configBuilder.getConfigParameters().getServiceName()))
                .then();
    }

    private Mono<JobResponse> scheduleNewJob(JobEvent event) {
        CustomJobFactory jobFactory = new CustomJobFactory(event);
        JobDetail jobDetail = JobBuilder.newJob(Task.class)
                .withIdentity(event.getJobId())
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(event.getJobId())
                .withSchedule(CronScheduleBuilder.cronSchedule(event.getCronRegExp()))
                .forJob(event.getJobId())
                .build();

    try {
        scheduler = (scheduler != null) ? scheduler : schedulerFactory.getScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.getListenerManager().addJobListener(new JobRuntimeListener(this, event));

        scheduler.scheduleJob( jobDetail, trigger);

        scheduler.start();

    } catch(SchedulerException exc){
        exc.printStackTrace();

    }
        return Mono.just(JobResponse.builder().build());
    }

    public Mono<JobEvent> processJobExecuted(JobExecution execution, JobEvent event) {

        return saveJobExecution(buildJobExecutionCanonical(execution))
                .flatMap(jobResponse -> saveJobEvent(event))
                .thenReturn(emitJobExecutedEvent(buildJobEventCanonical(event,execution), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    private Mono<JobResponse> saveJobExecution(JobExecutionCanonical jobExecutionCanonical) {
        return  Mono.just(configBuilder.getJobExecutionRepository()
                .save(buildJobExecutionCanonical(jobExecutionCanonical)).block())
                .flatMap(jobEvent -> Mono.just(JobResponse.builder()
                            .jobExecutionCanonical(Arrays.asList(jobExecutionCanonical))
                        .build()));
    }

    private Mono<JobEvent> saveJobEvent(JobEvent event) {
        return Mono.just(configBuilder.getEventRepository()
                .save(event).block());
    }

    private JobEvent emitJobExecutedEvent(JobEventCanonical jobEventCanonical, String uuid, String serviceName) {
        return Mono.just(new JobExecutedEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(buildJobEvent(jobEventCanonical))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(buildJobEvent(jobEventCanonical)).block();
    }

    private Mono<JobResponse> emitJobScheduledEvent(JobEventCanonical jobEventCanonical, String uuid, String serviceName) {
        return Mono.just(new JobScheduledEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(buildJobEvent(jobEventCanonical))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobResponse.builder().build());
    }
}
