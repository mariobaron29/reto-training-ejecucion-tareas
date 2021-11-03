package com.sofka.ejecuciontareas.domain.canonical;


import com.sofka.ejecuciontareas.domain.Job;
import reactor.core.publisher.Mono;

public interface JobCanonicalRepository {

    Mono<Job> save(Job data);
    Mono<Job> findById(String id);

}
