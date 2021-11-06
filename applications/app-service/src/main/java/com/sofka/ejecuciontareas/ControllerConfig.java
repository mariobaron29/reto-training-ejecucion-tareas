package com.sofka.ejecuciontareas;

import com.sofka.ejecuciontareas.command.JobController;
import com.sofka.ejecuciontareas.common.event.EventsGateway;
import com.sofka.ejecuciontareas.configbuilder.ConfigBuilder;
import com.sofka.ejecuciontareas.configbuilder.ConfigParameters;
import com.sofka.ejecuciontareas.domain.canonical.event.EventCanonicalRepository;
import com.sofka.ejecuciontareas.domain.canonical.jobexecution.JobExecutionRepository;
import com.sofka.ejecuciontareas.query.JobExecutionQueryController;
import lombok.extern.java.Log;
import org.reactivecommons.async.impl.config.RabbitMqConfig;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Log
@Configuration
@Import(RabbitMqConfig.class)
public class ControllerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${component.name}")
    private String componentName;

    @Value("${service.name}")
    private String serviceName;

    @Value("${operation}")
    private String operation;

    @Value("${rabbit.ssl.protocol}")
    private String tls;

    @Bean
    public JobController jobController(
            EventsGateway eventsGateway,
            JobExecutionRepository jobRepository
    ) {
        return new JobController(
                ConfigBuilder.builder()
                        .eventsGateway(eventsGateway)
                        .jobExecutionRepository(jobRepository)
                        .configParameters(ConfigParameters.builder()
                                .componentName(componentName)
                                .serviceName(serviceName)
                                .operation(operation)
                                .build())
                        .build()
        );
    }

    @Bean
    public JobExecutionQueryController jobExecutionQueryController(
            EventsGateway eventsGateway,
            JobExecutionRepository jobRepository,
            EventCanonicalRepository eventRepository
    ) {
        return new JobExecutionQueryController(
                ConfigBuilder.builder()
                        .eventsGateway(eventsGateway)
                        .jobExecutionRepository(jobRepository)
                        .eventRepository(eventRepository)
                        .configParameters(ConfigParameters.builder()
                                .componentName(componentName)
                                .serviceName(serviceName)
                                .operation(operation)
                                .build())
                        .build()
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapperImp();
    }

}
