package com.sofka.ejecuciontareas.reactive;


import com.sofka.ejecuciontareas.command.JobController;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.async.api.HandlerRegistry;
import org.reactivecommons.async.impl.config.annotations.EnableMessageListeners;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.reactivecommons.async.api.HandlerRegistry.register;

@Configuration
@EnableMessageListeners
@RequiredArgsConstructor
public class EventsSubscriptionsConfig {

    private final EventMapper eventMapper;
    private final JobController controller;
    private final String ROUTING_KEY = "tareas.job.created";

    @Bean
    public HandlerRegistry eventSubscriptions() {
        return register().listenEvent(ROUTING_KEY,
                event -> controller.processCreateJobEvent(eventMapper.buildCanonicalIn(event.getData())),
                DomainEvent.class);
    }

}

