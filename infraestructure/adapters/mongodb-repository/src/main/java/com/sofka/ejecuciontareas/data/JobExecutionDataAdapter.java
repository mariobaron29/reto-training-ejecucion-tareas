package com.sofka.ejecuciontareas.data;

import com.sofka.ejecuciontareas.domain.canonical.jobexecution.JobExecutionRepository;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import com.sofka.ejecuciontareas.repository.mongo.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class JobExecutionDataAdapter
        extends AdapterOperations<JobExecution, JobExecutionData, String, JobExecutionDataRepository>
        implements JobExecutionRepository {


    private final ObjectMapper objectMapper;

    @Autowired
    public JobExecutionDataAdapter(JobExecutionDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, JobExecution.JobExecutionBuilder.class).build());
        this.objectMapper = mapper;

    }

    @Override
    public Flux<JobExecution> findAll() {
        return doQueryMany(repository.findAll());
    }
}
