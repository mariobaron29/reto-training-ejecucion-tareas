package com.sofka.ejecuciontareas.query;

import com.sofka.ejecuciontareas.common.event.notification.HeaderFactory;
import com.sofka.ejecuciontareas.configbuilder.ConfigBuilder;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobFactory;
import com.sofka.ejecuciontareas.domain.response.JobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;


@Log
@RequiredArgsConstructor
public class JobExecutionQueryController implements JobFactory, HeaderFactory {

    private final ConfigBuilder configBuilder;

    public Flux<JobExecution> processFindAllJobExecutions() {

        return configBuilder.getJobExecutionRepository().findAll();

    }

    public Mono<JobResponse> processFindJobExecutionById(String id) {
        return configBuilder.getJobExecutionRepository()
                .findById(id)
                .flatMap(job -> Mono.just(JobResponse.builder()
                        .jobExecutionCanonical(Arrays.asList( buildJobExecutionCanonical(job)  ))
                        .build()));
    }

    public Mono<JobResponse> processFindJobExecutionByJobId(String id) {
        return configBuilder.getJobExecutionRepository()
                .findById(id)
                .flatMap(job -> Mono.just(JobResponse.builder()
                        .jobExecutionCanonical(Arrays.asList(buildJobExecutionCanonical(job)))
                        .build()));
    }

}
