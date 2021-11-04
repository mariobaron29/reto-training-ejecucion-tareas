package com.sofka.ejecuciontareas.data;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface JobExecutionDataRepository extends ReactiveCrudRepository<JobExecutionData, String>,
        ReactiveQueryByExampleExecutor<JobExecutionData> {

}
