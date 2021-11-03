package com.sofka.ejecuciontareas.reactive;


import com.sofka.ejecuciontareas.JobController;
import com.sofka.ejecuciontareas.common.event.JobCreatedEvent;
import com.sofka.ejecuciontareas.common.event.notification.CanonicalNotification;
import com.sofka.ejecuciontareas.domain.canonical.JobCanonical;
import com.sofka.ejecuciontareas.reactive.dto.message.EventMessage;
import lombok.RequiredArgsConstructor;
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
    private final String ROUTING_KEY = "administracion.tareas";

    @Bean
    public HandlerRegistry eventSubscriptions() {

        /*controller.processCreateJobEvent(JobCreatedEvent.builder()
                        .canonical(CanonicalNotification.builder()
                                .data(JobCanonical.builder()
                                        .id("d1f4315d-9e0d-425e-b9c7-a93fa4edef1e")
                                        .cronRegExp("0 * * * * ?")
                                        .email("mariobaron@gmail.com")
                                        .timeZone("UTC−05:00")
                                        .url("www.facebook.com")
                                        .build())
                                .build())
                .build());*/

        return register().listenEvent(JobCreatedEvent.EVENT_NAME,
                event -> controller.processCreateJobEvent(eventMapper.buildCanonicalIn(event.getData())),
                EventMessage.class);
    }

}

