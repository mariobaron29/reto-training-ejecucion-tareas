package com.sofka.ejecuciontareas;

import com.sofka.ejecuciontareas.common.event.JobCreatedEvent;
import com.sofka.ejecuciontareas.common.event.JobScheduledEvent;
import com.sofka.ejecuciontareas.common.event.JobUpdatedEvent;
import com.sofka.ejecuciontareas.common.event.notification.CanonicalNotification;
import com.sofka.ejecuciontareas.common.event.notification.HeaderFactory;
import com.sofka.ejecuciontareas.configbuilder.ConfigBuilder;
import com.sofka.ejecuciontareas.domain.Job;
import com.sofka.ejecuciontareas.domain.JobFactory;
import com.sofka.ejecuciontareas.domain.canonical.JobCanonical;
import com.sofka.ejecuciontareas.domain.response.JobResponse;
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

    public Mono<Void> processCreateJobEvent(JobCreatedEvent event) {
JobCanonical canonical = (JobCanonical) event.getCanonical().getData();
        return scheduleNewJob(canonical)
                .flatMap(jobResponse -> emitJobScheduledEvent(canonical, uuid, configBuilder.getConfigParameters().getServiceName()).then());
    }

    private Mono<JobResponse> scheduleNewJob(JobCanonical jobCanonical) {
        /*CronExpression expression = new CronExpression("10 * * * * *");
        var result = expression.next(LocalDateTime.now());
        System.out.println(result);*/
        //Create instance of factory
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
try {
    //Get scheduler
    Scheduler scheduler = schedulerFactory.getScheduler();

    //Create JobDetail object specifying which Job you want to execute
    JobDetail jobDetail = JobBuilder.newJob(Task.class)
                .withIdentity(jobCanonical.getId())
            .build();

    //Associate Trigger to the Job
    CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(jobCanonical.getId())
            .withSchedule(CronScheduleBuilder.cronSchedule(jobCanonical.getCronRegExp()))
            .forJob(jobCanonical.getId())
            .build();

    //Pass JobDetail and trigger dependencies to schedular
    scheduler.scheduleJob(jobDetail, trigger);

    //Start schedular
    scheduler.start();
}catch (SchedulerException exc){
    exc.printStackTrace();

}
        return configBuilder.getJobRepository()
                        .save(buildJob(jobCanonical))
                .then(Mono.just(JobResponse.builder()
                        .jobCanonical(jobCanonical)
                        .build()));
    }


    private Mono<JobResponse> emitJobScheduledEvent(JobCanonical job, String uuid, String serviceName) {
        return Mono.just(new JobScheduledEvent(CanonicalNotification.<Job>builder()
                        .header(getHeader(uuid, serviceName))
                        .data(Job.builder()
                                    .id(job.getId())
                                    .cronRegExp(job.getCronRegExp())
                                    .email(job.getEmail())
                                    .status(job.getStatus())
                                    .timeZone(job.getTimeZone())
                                    .url(job.getUrl())
                                .build())
                        .build()))
                .flatMap(event -> configBuilder.getEventsGateway().emit(event))
                .thenReturn(JobResponse.builder()
                        .jobCanonical(job)
                        .build());
    }

}
