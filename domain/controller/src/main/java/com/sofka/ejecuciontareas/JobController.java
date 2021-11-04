package com.sofka.ejecuciontareas;

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
import com.sofka.ejecuciontareas.tasks.CustomJobFactory;
import com.sofka.ejecuciontareas.tasks.Task;
import com.sofka.ejecuciontareas.tasks.listener.JobRuntimeListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import reactor.core.publisher.Mono;

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
                .flatMap(jobResponse -> emitJobScheduledEvent(buildJobEventCanonical(event), uuid, configBuilder.getConfigParameters().getServiceName()).then());
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
        return Mono.just(JobResponse.builder().build());// TODO return response
    }

    public Mono<JobEvent> processJobExecuted(JobExecution execution, JobEvent event) {

        return saveJobExecution(buildJobExecution(execution))
                .flatMap(jobResponse -> saveJobEvent(event))
                .flatMap(jobEventResponse ->
                        emitJobExecutedEvent(buildJobEventCanonical(event), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    public Mono<JobResponse> processJobScheduled(JobEvent event) {

        return saveJobEvent(event)
                .flatMap(jobEventResponse ->
                        emitJobScheduledEvent(buildJobEventCanonical(event), uuid, configBuilder.getConfigParameters().getServiceName()));
    }

    private Mono<JobResponse> saveJobExecution(JobExecutionCanonical jobExecutionCanonical) {
        return  Mono.just(configBuilder.getJobExecutionRepository()
                .save(buildJobExecution(jobExecutionCanonical)).block())
                .flatMap(jobEvent -> Mono.just(JobResponse.builder()
                            .jobExecutionCanonical(jobExecutionCanonical)
                        .build()));
    }

    private Mono<JobEvent> saveJobEvent(JobEvent event) {
        return Mono.just(configBuilder.getEventRepository()
                .save(event).block());
    }

    private Mono<JobEvent> emitJobExecutedEvent(JobEventCanonical jobEventCanonical, String uuid, String serviceName) {
        return Mono.just(new JobExecutedEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(buildJobEvent(jobEventCanonical))
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(buildJobEvent(jobEventCanonical));
    }

    private Mono<JobResponse> emitJobScheduledEvent(JobEventCanonical jobEventCanonical, String uuid, String serviceName) {
        return Mono.just(new JobScheduledEvent(CanonicalNotification.<JobEvent>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(JobEvent.builder()
                                .eventId(jobEventCanonical.getEventId())
                                .eventName(jobEventCanonical.getEventName())
                                .jobId(jobEventCanonical.getJobId())
                                .cronRegExp(jobEventCanonical.getCronRegExp())
                                .email(jobEventCanonical.getEmail())
                                .status(jobEventCanonical.getStatus())
                                .timeZone(jobEventCanonical.getTimeZone())
                                .url(jobEventCanonical.getUrl())
                                .build())
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobResponse.builder()
                        //.jobExecutionCanonical(jobEvent)
                        .build());
    }
}
