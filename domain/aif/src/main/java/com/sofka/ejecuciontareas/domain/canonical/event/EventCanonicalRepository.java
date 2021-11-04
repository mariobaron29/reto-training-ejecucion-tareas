package com.sofka.ejecuciontareas.domain.canonical.event;


import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import reactor.core.publisher.Mono;

public interface EventCanonicalRepository {

    Mono<JobEvent> save(JobEvent data);
    Mono<JobEvent> findById(String id);


}
