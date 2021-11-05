package com.sofka.ejecuciontareas.query;

import com.sofka.ejecuciontareas.common.event.notification.HeaderFactory;
import com.sofka.ejecuciontareas.configbuilder.ConfigBuilder;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;


@Log
@RequiredArgsConstructor
public class JobExecutionQueryController implements JobFactory, HeaderFactory {

    private final ConfigBuilder configBuilder;

    public Flux<JobExecution> processFindAllJobExecutions() {

        return findAllJobExecutions();

    }

    private Flux<JobExecution> findAllJobExecutions() {
        return configBuilder.getJobExecutionRepository().findAll();
     }

}
