package com.sofka.ejecuciontareas.domain.canonical.jobexecution;


import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobExecutionRepository {

    Mono<JobExecution> save(JobExecution data);
    Mono<JobExecution> findById(String id);
    Flux<JobExecution> findAll();

}
