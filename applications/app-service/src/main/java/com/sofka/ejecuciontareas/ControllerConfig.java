package com.sofka.ejecuciontareas;

import com.sofka.ejecuciontareas.common.event.EventsGateway;
import com.sofka.ejecuciontareas.configbuilder.ConfigBuilder;
import com.sofka.ejecuciontareas.configbuilder.ConfigParameters;
import com.sofka.ejecuciontareas.domain.canonical.JobCanonicalRepository;
import com.sofka.ejecuciontareas.reactive.custom_reactive_listener.CustomMessageConverter;
import com.sofka.ejecuciontareas.reactive.custom_reactive_listener.CustomMessageConverterImpl;
import lombok.extern.java.Log;
import org.reactivecommons.async.impl.config.RabbitMqConfig;
import org.reactivecommons.async.impl.converters.json.ObjectMapperSupplier;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
            JobCanonicalRepository jobRepository
    ) {
        return new JobController(
                ConfigBuilder.builder()
                        .eventsGateway(eventsGateway)
                        .jobRepository(jobRepository)
                        .configParameters(ConfigParameters.builder()
                                .componentName(componentName)
                                .serviceName(serviceName)
                                .operation(operation)
                                .build())
                        .build()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomMessageConverter customMessageConverter(ObjectMapperSupplier objectMapperSupplier) {
        return new CustomMessageConverterImpl(objectMapperSupplier.get());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapperImp();
    }

}
