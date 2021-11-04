package com.sofka.ejecuciontareas.event;

import com.sofka.ejecuciontareas.domain.canonical.event.EventCanonicalRepository;
import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import com.sofka.ejecuciontareas.repository.mongo.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JobEventDataAdapter
        extends AdapterOperations<JobEvent, JobEventData, String, JobEventDataRepository>
        implements EventCanonicalRepository {


    private final ObjectMapper objectMapper;

    @Autowired
    public JobEventDataAdapter(JobEventDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, JobEvent.JobEventBuilder.class).build());
        this.objectMapper = mapper;

    }

}
